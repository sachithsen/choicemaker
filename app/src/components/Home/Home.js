import * as React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import { CardActionArea } from "@mui/material";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import useMediaQuery from "@mui/material/useMediaQuery";
import { useTheme } from "@mui/material/styles";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./../AuthContext";
import { getAllUsers } from "./../../api/UserApi";
import { getAllSessions, createSession, getSessionById } from "../../api/SessionApi";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function Home() {
  const [openCreate, setOpenCreate] = React.useState(false);
  const [openJoin, setOpenJoin] = React.useState(false);
  const navigate = useNavigate();
  const [personName, setPersonName] = React.useState([]);
  const [sessionName, setSessionName] = React.useState([]);
  const [sessionId, setSessionId] = React.useState([]);
  const [userList, setUserList] = React.useState([]);
  const [sessions, setSessions] = React.useState([]);
  const { user } = useAuth();
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("md"));
  const [currentUser, setCurrentUser] = React.useState(null);
  const [sessionUsers, setSessionUsers] = React.useState("");
  const [currentSessionId, setCurrentSessionId] = React.useState(""); 

  React.useEffect(() => {
    setCurrentUser(JSON.parse(localStorage.getItem("currentUser")));
    getAllUsers()
      .then((response) => {
        console.log("All users : ", response);
        if (response) {
          localStorage.setItem("userList", JSON.stringify(response));
          setUserList(response);
        }
      })
      .catch((error) => console.error("Error:", error));
  }, []);

  React.useEffect(() => {
    const user = JSON.parse(localStorage.getItem("currentUser"));
    getAllSessions(user.username)
      .then((response) => {
        console.log("All active sessions : ", response);
        if (response) {
          setSessions(response);
        }
      })
      .catch((error) => console.error("Error:", error));
  }, []);

  const handleClickOpenCreate = () => {
    setOpenCreate(true);
  };

  const handleCloseCreate = () => {
    setOpenCreate(false);
    const data = {
      sessionAdmin: currentUser.username,
      sessionParticipants: personName,
    };
    console.log(data);
    createSession(data)
      .then((response) => {
        console.log("Current session : ", response);
        if (response) {
          localStorage.setItem("currentSession", JSON.stringify(response));
          setSessionId(response.sessionId);
        }
        // TODO: Clear the local storage currentSession
        const data = { sessionId: response.sessionId };
        navigate("/session", { state: data });
      })
      .catch((error) => console.error("Error:", error));
  };

  const handleChangeCreate = (event) => {
    const {
      target: { value },
    } = event;
    setPersonName(typeof value === "string" ? value.split(",") : value);
  };

  const handleClickOpenJoin = () => {
    setOpenJoin(true);
  };

  const handleCloseJoin = () => {
    setOpenJoin(false);
    console.log(sessionName);
    getSessionById(currentSessionId)
      .then((response) => {
        console.log("Current session : ", response);
        if (response) {
          localStorage.setItem("currentSession", JSON.stringify(response));
          setSessionId(response.sessionId);
        }
        // TODO: Clear the local storage currentSession
        const data = { sessionId: response.sessionId };
        navigate("/session", { state: data });
      })
      .catch((error) => console.error("Error:", error));
  };

  const handleChangeJoin = (event) => {
    const {
      target: { value },
    } = event;
    console.log(value);
    setCurrentSessionId(value);
    setSessionName(value);
  };

  function getStyles(name, personName, theme) {
    return {
      fontWeight:
        personName.indexOf(name) === -1
          ? theme.typography.fontWeightRegular
          : theme.typography.fontWeightMedium,
    };
  }

  return (
    <div>
      <Box
        sx={{
          flexGrow: 1,
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
          paddingTop: 3 + "em",
        }}
      >
        <Grid container>
          <Grid
            item
            xs={6}
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Card sx={{ maxWidth: 345, maxHeight: 350 }}>
              <CardActionArea onClick={handleClickOpenCreate}>
                <CardMedia
                  component="img"
                  height="140"
                  image="./create.jpg"
                  alt="create session"
                />
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    Create
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Create a session to choose a place with your friends.
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
          <Grid
            item
            xs={6}
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Card sx={{ maxWidth: 345, maxHeight: 350 }}>
              <CardActionArea onClick={handleClickOpenJoin}>
                <CardMedia
                  component="img"
                  height="140"
                  image="./join.jpg"
                  alt="join session"
                />
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    Join
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Join a session to choose a place with your friends.
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        </Grid>
      </Box>
      <Dialog
        fullScreen={fullScreen}
        open={openCreate}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          {"Select users for the session"}
        </DialogTitle>
        <DialogContent>
          <FormControl sx={{ m: 1, width: 300 }}>
            <InputLabel id="demo-multiple-name-label">Name</InputLabel>
            <Select
              labelId="demo-multiple-name-label"
              id="demo-multiple-name"
              multiple
              value={personName}
              onChange={handleChangeCreate}
              input={<OutlinedInput label="Name" />}
              MenuProps={MenuProps}
            >
              {userList.map((user) => (
                <MenuItem
                  key={user.id}
                  value={user.username}
                  style={getStyles(user.username, personName, theme)}
                >
                  {user.firstName + " " + user.lastName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseCreate} autoFocus>
            Create
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog
        fullScreen={fullScreen}
        open={openJoin}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          {"Select session to join"}
        </DialogTitle>
        <DialogContent>
          <FormControl sx={{ m: 1, width: 300 }}>
            <InputLabel id="demo-multiple-name-label">Session Name</InputLabel>
            <Select
              labelId="multiple-session-label"
              id="session-select"
              value={sessionName}
              onChange={handleChangeJoin}
              input={<OutlinedInput label="Session Name" />}
              MenuProps={MenuProps}
            >
              {sessions.map((session) => (
                <MenuItem
                  key={session.sessionId}
                  value={session.sessionId}
                  style={getStyles(session.sessionName, sessionName, theme)}
                >
                  {session.sessionName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseJoin} autoFocus>
            Join
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default Home;
