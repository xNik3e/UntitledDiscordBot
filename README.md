<h1 align="center"><strong>UntitledDiscordBot Mobile App</strong></h1>
<p align="center">
  <img src="https://img.shields.io/badge/Version-1.0.0-blue.svg" alt="Version">
  <img src="https://img.shields.io/badge/Build-passing-brightgreen.svg" alt="Build">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License"></p>
  <br>
<p align="center">A mobile app for the <a href="https://github.com/xNik3e/UDB-Spring">UntitledDiscordBot.</a></p>
<br>
<p>
The aim of the project was to design and produce a mobile application that would support Discord server administrators in the process of initializing an external application - Chatbot. The project consists of two parts, the server layer is located in a separate repository at <strong><a href="https://github.com/xNik3e/UDB-Spring">this address</a></strong> and is the result of engineering work carried out at the end of studies.
</p>
<p>The user, in the form of an administrator, is able to adjust access to implemented commands for individual roles, users, or text channels, as well as change the basic configuration in the form of automatic trigger and response removal after a specified time, or command prefix.</p>
<p>The mobile application layer is characterized by a modern look and an intuitive user interface. Authorization with Discord is carried out according to the OAuth 2.0 standard, and the application communicates with the Bot instance to retrieve data on connected servers and extract saved settings from the database.</p>

<h2>Technologies Used</h2>

- Android
- Java
- [AppAuth](https://github.com/openid/AppAuth-Android)
- [Lottie](https://lottiefiles.com/)
- [FuzzyWuzzy](https://github.com/xdrop/fuzzywuzzy)
- [BottomNavMenu](https://github.com/st235/ExpandableBottomBar)
- [CarouselView](https://github.com/ImaginativeShohag/Why-Not-Image-Carousel)
- [No Internet Connection](https://github.com/ImaginativeShohag/Oops-No-Internet)
- [Retrofit](https://github.com/square/retrofit)
- [OkHttp](https://github.com/square/okhttp)
- [Glide](https://github.com/bumptech/glide)

<h2>Gallery</h2>
<div align="center">
  <img src="media\udb_auth.gif" alt="Auth" width="50%" height="50%"> 
  <p><em>Auth with Discord</em></p>
</div>
<br>
<div align="center">
  <img src="media\udb_add_bot.gif" alt="Add a Bot" width="50%" height="50%"> 
  <p><em>Add a Bot to selected server</em></p>
</div>
<br>
<div align="center">
  <img src="media\udb_default_permissions.gif" alt="Default permissions" width="50%" height="50%"> 
  <p><em>Default permissions setting screen</em></p>
</div>
<div align="center">
  <img src="media\udb_channel_permissions.gif" alt="Channel permissions" width="50%" height="50%"> 
  <p><em>Channel permissions setting screen</em></p>
</div>
<br>

