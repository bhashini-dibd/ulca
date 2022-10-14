import { io } from "socket.io-client";

export const socketConnection = (socketEndpoint, path) => {
  const socket = io(socketEndpoint, {
    transport: ["websocket"],
    upgrade: false,
    path,
  });
  socket.once('connect',()=>{
      console.log('socket connected!');
  })
  return socket;
};

export const disconnectSocket = (socket) => {
  socket.disconnect();
};
