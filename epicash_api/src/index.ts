import * as http from "http";
import App from "./app";

const port = process.env.PORT || 3070;

App.set("port", port);
const server = http.createServer(App);
server.listen(port);

server.on("listening", function(): void {
    const addr = server.address();
    const bind = (typeof addr === "string") ? `pipe ${addr}` : (addr)?`port ${addr.port}`:`port ${port}`;
});

module.exports = App;