import * as React from "react";
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import DiningIcon from "@mui/icons-material/Dining";
import ShuffleIcon from "@mui/icons-material/Shuffle";
import CloseIcon from "@mui/icons-material/Close";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContentText from "@mui/material/DialogContentText";
import TextField from "@mui/material/TextField";
import Slide from "@mui/material/Slide";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardMedia from "@mui/material/CardMedia";
import { useNavigate, useLocation } from "react-router-dom";
import SockJsClient from "react-stomp";
import { createSubmission, selectSubmission } from "../api/SubmissionApi";
import { styled } from "@mui/material/styles";

const SOCKET_URL = "http://localhost:8080/ws-message";

const styles = {
  fabAdd: {
    position: "fixed",
    top: "80px",
    right: "20px",
  },
  fabClose: {
    position: "fixed",
    top: "80px",
    right: "200px",
  },
};

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

function Room() {
  const [openAdd, setOpenAdd] = React.useState(false);
  const [openCloseSession, setCloseSession] = React.useState(false);
  const [isDisable, setDisable] = React.useState(false);
  const [cards, setCards] = React.useState([]);
  const [text, setText] = React.useState("");
  const [highlightedCard, setHighlightedCard] = React.useState(null);
  const navigate = useNavigate();
  const [message, setMessage] = React.useState("You server message here.");
  const sockJsClientRef = React.useRef(null);
  const [selectedCard, setSelectedCard] = React.useState(null);
  const [openClosingDialog, setOpenClosingDialog] = React.useState(false);
  const [endSessionVisible, setEndSessionVisible] = React.useState(false);
  const [currentSession, setCurrentSession] = React.useState(null);
  let currentSessionId = null;
  const { state } = useLocation();

  let onConnected = () => {
    console.log("Web socket Connected!!");
  };

  const BootstrapDialog = styled(Dialog)(({ theme }) => ({
    "& .MuiDialogContent-root": {
      padding: theme.spacing(2),
    },
    "& .MuiDialogActions-root": {
      padding: theme.spacing(1),
    },
  }));

  let onMessageReceived = (msg) => {
    console.log(msg);
    if (msg.eventType === "ADD_CHOICE") {
      const data = JSON.parse(msg.data);
      console.log("Event data : ", data);
      if (data.text) {
        const newCard = (
          <Card key={data.id} sx={{ maxWidth: 345, minWidth: 250 }}>
            <CardMedia
              sx={{ height: 140 }}
              image="./resturent.jpg"
              title="resturent"
            />
            <CardContent>
              <Typography gutterBottom variant="h5" component="div">
                {data.text}
              </Typography>
            </CardContent>
          </Card>
        );

        setCards([...cards, newCard]);
        setText("");
      }
    }

    if (msg.eventType === "END_SESSION") {
      const data = JSON.parse(msg.data);
      console.log("End session data : ", data);
      cards.forEach((card) => {
        if (parseInt(card.key) === data.id) {
          console.log("Match found !!");
          setSelectedCard(card);
          setOpenClosingDialog(true);
        }
      });
    }
  };

  // const handleCloseDialog = () => {
  //   setOpenClosingDialog(false);
  //   navigate("/", { replace: true });
  //   // remove all session related data and do session termination
  // };

  const handleSessionTermination = () => {
    localStorage.setItem("currentSession", null);
    localStorage.setItem("cards", []);
    setOpenClosingDialog(false);
    navigate("/", { replace: true });
    // remove all session related data and do session termination
  };

  React.useEffect(() => {
    const currentSession = JSON.parse(localStorage.getItem("currentSession"));
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    setCurrentSession(currentSession);
    if (currentSession && currentSession.sessionId) {
      currentSessionId = currentSession.sessionId;
    }
    if (currentSession.sessionAdmin === currentUser.username) {
      setEndSessionVisible(true);
    }
  }, []);

  React.useEffect(() => {
    localStorage.setItem("cards", JSON.stringify(cards));
  }, [cards]);

  const onAddClick = () => {
    setOpenAdd(true);
  };

  // const onRandomClick = () => {
  //   const randomIndex = Math.floor(Math.random() * cards.length);
  //   const randomCard = cards[randomIndex];

  //   setHighlightedCard(randomCard);
  //   setDisable(true);
  // };

  const onCloseClick = () => {
    //setCloseSession(true);
    const randomIndex = Math.floor(Math.random() * cards.length);
    const randomCard = cards[randomIndex];
    console.log("Card : ", randomCard.key);
    selectSubmission(randomCard.key)
      .then((response) => {
        if (response) {
          console.log("Session end : ", response);
        }
      })
      .catch((error) => console.error("Error:", error));
  };

  // Function to send a message
  const sendMessage = () => {
    if (sockJsClientRef.current) {
      const jsonObject = {
        message: "Hello Sachith!! From Client.",
      };

      // Convert the JSON object to a string
      const jsonMessage = JSON.stringify(jsonObject);
      sockJsClientRef.current.sendMessage("/app/sendMessage", jsonMessage);
    }
  };

  // const handleClose = () => {
  //   setCloseSession(false);
  //   navigate("/", { replace: true });
  // };
  const handleSubmit = () => {
    const session = JSON.parse(localStorage.getItem("currentSession"));
    const user = JSON.parse(localStorage.getItem("currentUser"));
    const data = {
      sessionId: session.sessionId,
      username: user.username,
      text: text,
    };
    createSubmission(data)
      .then((response) => {
        if (response) {
          console.log("Current submission : ", response);
        }
      })
      .catch((error) => console.error("Error:", error));
    setOpenAdd(false);
  };
  const handleTextChange = (event) => {
    setText(event.target.value);
  };

  return (
    <div>
      <Box>
        <Fab
          color="primary"
          variant="extended"
          style={styles.fabAdd}
          onClick={onAddClick}
          disabled={isDisable}
        >
          <DiningIcon sx={{ mr: 1 }} />
          Add Choice
        </Fab>
        <div>
          {endSessionVisible && (
            <Fab
              color="error"
              variant="extended"
              style={styles.fabClose}
              onClick={onCloseClick}
            >
              <CloseIcon sx={{ mr: 1 }} />
              END Session
            </Fab>
          )}
        </div>
        <div style={{ display: "flex", flexWrap: "wrap", marginTop: "80px" }}>
          {cards.map((card, index) => (
            <div
              key={index}
              style={{
                margin: "10px",
                border: highlightedCard === card ? "2px solid green" : "none",
              }}
            >
              {card}
            </div>
          ))}
        </div>
      </Box>
      <Dialog
        TransitionComponent={Transition}
        keepMounted
        open={openAdd}
        // onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle id="responsive-dialog-title">
          {"Add the resturent of your choice for the selection?"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText></DialogContentText>
          <TextField
            autoFocus
            onChange={handleTextChange}
            margin="dense"
            id="name"
            label="Your choice"
            type="text"
            fullWidth
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleSubmit} autoFocus>
            Add
          </Button>
        </DialogActions>
      </Dialog>
      {/* <Dialog
        open={openCloseSession}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle>{"Closing Session?"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-slide-description">
            Click close button to close the session for all the users.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Close</Button>
        </DialogActions>
      </Dialog> */}

      <BootstrapDialog
        open={openClosingDialog}
        onClose={handleSessionTermination}
      >
        <DialogTitle>{"TODAY'S RESTURENT"}</DialogTitle>
        <DialogContent>{selectedCard}</DialogContent>
        <Button
          variant="outlined"
          onClick={handleSessionTermination}
          color="success"
        >
          DONE
        </Button>
      </BootstrapDialog>
      <div>
        <SockJsClient
          url={SOCKET_URL}
          ref={sockJsClientRef}
          topics={[`/topic/${state.sessionId}`]}
          onConnect={onConnected}
          onDisconnect={console.log("Disconnected!")}
          onMessage={(msg) => onMessageReceived(msg)}
          autoReconnect={true}
          debug={false}
        />
      </div>
    </div>
  );
}

export default Room;
