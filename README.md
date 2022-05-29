# Socket Project
基于Java的即时通讯系统。C/S结构的、运用Java语言和socket管道通信实现的多用户即时通信系统，可以进行私聊，群发，显示在
线用户列表，发送文件等功能。
## 技术方案
1. 显示用户列表：服务器会统计当前的在线用户并将信息封装成对象(实现序列化接口)，将消息对象通过
socket发送给用户
2. 用户私聊功能：用户选定私聊用户输入信息，将输入的信息封装成消息对象，发送给服务器，服务器接收信
息转发给目标用户，实现用户私聊。
3. 群发消息：用户选择群发消息功能，将输入的信息封装成发送给服务器，服务器分别转发给好友，实现消息
的群发。
4. 发送文件：输入本地文件路径，将文件以字节流的形式传输到服务器，服务器下发通知，询问是否接受，要
求用户输入保存路径，将文件发送给用户。
