<div align="center" id="top"> 
  <img src="./.github/app.gif" alt="T DEV 700 LYO_10" />

  &#xa0;

  <!-- <a href="https://tdev700lyo_10.netlify.app">Demo</a> -->
</div>

<h1 align="center">T DEV 700 LYO_10 (Cash manager)</h1>


<!-- Status -->

<!-- <h4 align="center"> 
	🚧  T DEV 700 LYO_10 🚀 Under construction...  🚧
</h4> 

<hr> -->

<p align="center">
  <a href="#dart-about">About</a> &#xa0; | &#xa0; 
  <a href="#sparkles-features-mobile">Features Mobile</a> &#xa0; | &#xa0;
  <a href="#sparkles-features-bank">Features Bank</a> &#xa0; | &#xa0;
  <a href="#rocket-technologies">Technologies</a> &#xa0; | &#xa0;
  <a href="#white_check_mark-requirements">Requirements</a> &#xa0; | &#xa0;
  <a href="#checkered_flag-starting">Starting</a> &#xa0; | &#xa0;
  <a href="#memo-license">License</a> &#xa0; | &#xa0;
</p>

<br>

## :dart: About ##

The aim of this project is to build a distant payment system that can receive and execute orders issued by
a terminal app on your phone.
<p align="center">
	<img width="765" alt="Cash manager Figma" src="https://user-images.githubusercontent.com/30879857/214324868-dadfb1b0-bd48-49a6-837a-8023648e1340.png">
</p>

## :sparkles: Features (MOBILE) ##

:heavy_check_mark: - Let you add articles to a cart,then proceed to payment;\
:heavy_check_mark: - Provide a way to configure the network location and the password of the server with a setting view;\
:heavy_check_mark: - Allow credit card and cheque payment;\
:heavy_check_mark: - A setting screen allowing the user to connect the terminal to the server (including login/password input fields);\
:heavy_check_mark: - A screen displaying the cash register,where the user adds articles and the bill is updated in real time;\
:heavy_check_mark: - A screen displaying the bill’stotal(no specificaction but proceed to payment);\
:heavy_check_mark: - A screen providing the user payment that allows to scan a card (NFC Reader) or a cheque (QR Code scanner) and displays the card payment status: pending authorization, payment accepted, payment refused;\

## :sparkles: Features (BANK) ##
<br>

:heavy_check_mark: - Receive requests;\
:heavy_check_mark: - Accept or refuse authentication based on stored data(any authentication method will do);\
:heavy_check_mark: - Fetch data from the bank account(any storage method will do);\
:heavy_check_mark: - Accept or refuse the payment, base on credit card/check information and money reserves;\
:heavy_check_mark: - Notify the mobile app in return;\
:heavy_check_mark: - update the user’s account according to the transaction;\


## :rocket: Technologies ##

The following tools were used in this project:

- [Expo](https://expo.io/)
- [Node.js](https://nodejs.org/en/)
- [TypeScript](https://www.typescriptlang.org/)
- [Android](https://www.android.com/intl/fr_fr/android-13/)
- [Java](https://www.java.com/fr/)
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://www.docker.com/)

## :white_check_mark: Requirements ##

Before starting :checkered_flag:, you need to have [Git](https://git-scm.com), [Android studio](https://developer.android.com/?hl=fr), [Node](https://nodejs.org/en/) and an emulator (In Android studio) installed.

## :checkered_flag: Starting ##

:rotating_light: WARNING ! The back was on a VPS so as not to configure the localhost connection between the back and the front :rotating_light:

(Back without Docker)

```bash 
# Clone this project
$ git clone git@github.com:EpitechMscProPromo2024/T-DEV-700-LYO_10.git

# Access
$ cd t-dev-700-lyo_10

# Install dependencies
$ yarn

#INSTALL .ENV (Files available in the below section) 

# Run the project
$ yarn start

# The API will initialize in the <http://localhost:4212>
```
<br>

(Back with Docker)
```bash
# Clone this project
$ git clone git@github.com:EpitechMscProPromo2024/T-DEV-700-LYO_10.git

# Access
$ cd t-dev-700-lyo_10

#INSTALL .ENV (Files available in the below section) 

# Install image and run the container
$ docker compose up

# The server will initialize in the <http://localhost:4212>
```
<br>

(Front 'application')

```bash 
# Clone this project
$ git clone git@github.com:EpitechMscProPromo2024/T-DEV-700-LYO_10.git

# Build the front in your IDE and use the App.
```

NEEDED FILES 
<br>

```bash
#T-DEV-700-LYO_10/.env (At the project root folder)
PGUSER:postgres
PGPASSWORD:postgres
PGDATABASE:cash
PGHOSTNAME:db
PGPORT:5432
PGHOST:localhost
```


```bash
#T-DEV-700-LYO_10/epicash_api/.env
USER_DB=postgres
DB=cash
DIALECT=postgres
PORT=4211
PASSWORD=postgres
```

## :memo: License ##

This project is under license from MIT. For more details, see the [LICENSE](LICENSE.md) file.


Made with :heart: by <a href="https://github.com/jojoricard" target="_blank">Joris RICARD</a>, <a href="https://github.com/ltournayre" target="_blank">Lola TOURNAYRE</a>, <a href="https://github.com/SimonParrot" target="_blank">Simon PARROT</a>, <a href="https://github.com/RobZ911" target="_blank">Robin RIGOGNE</a> and <a href="https://github.com/BTAVIOT" target="_blank">Baptiste TAVIOT</a>

&#xa0;

<a href="#top">Back to top</a>
