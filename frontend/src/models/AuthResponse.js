class AuthResponse {
    constructor(token, isAuthenticated) {
        this.token = token;
        this.isAuthenticated = isAuthenticated;
    }
}

export default AuthResponse;