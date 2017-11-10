<p align="center">
  <img src="/garbage/favicon.png" />
  <br />
  <strong>inpacker</strong>
  <br />
  <a href="https://travis-ci.org/dreyman/inpacker">
    <img src="https://travis-ci.org/dreyman/inpacker.svg?branch=master" />
  </a>
<p>

[Inpacker](https://inpacker.herokuapp.com) is an app for downloading images and videos from instagram.com and 500px.com.

<p align="center">
  <img src="/garbage/overview2.gif" height="480" />
</p>

### Run locally
```bash
./gradlew build
java -jar web/build/libs/web.jar
```
(in order to download from 500px.com add your consumer key to [application.properties](/web/src/main/resources/application.properties))
