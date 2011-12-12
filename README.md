# Force.com module for Play! Framework

# Usage

This module is not yet registered as an official Play! module and it depends on a fork of the Force.com WSC module. So here's how to build it and test it:

## Clone this repo and submodules

    $ git clone --recursive git@github.com:jesperfj/force-play-module.git

## Build the forked version of WSC

    $ cd force-play-module/wsc
    $ mvn install

(comment out the gpg plugin in `pom.xml` if you don't want to deal with it)

## Build the Play! module

    $ cd ..
    $ play deps --sync && play build-module

## Set up OAuth config

You'll need a Force.com developer account. If you don't have one [go and sign up now](http://www.developerforce.com/events/regular/registration.php?d=70130000000EjHb) (it's free).

Once you have an account, sign in and:

1. Click on "Admin User" drop-down in upper-right
2. Select Setup
3. In the left-side navigation pane, under "App Setup", click on "Develop"
4. Select "Remote Access"
5. Click on "New"
6. Choose any name for your application
7. Choose any callback URL (you'll need to set this properly when web server flow is supported)
8. Choose some contact email
9. Click "Save"
10. Copy "Consumer Key" for use later
11. Click on "Click to reveal" and copy "Consumer Secret" for use later.

Now set up environment variables using the consumer key and secret:

    $ export FORCE_OAUTH_KEY="consumer key"
    $ export FORCE_OAUTH_SECRET="consumer secret"
    $ export APP_URI="http://localhost:9000"

`APP_URI` is necessary to determine redirect URI. You must set redirect URI in remote access config to `http://localhost:9000/_auth`

### Test it out

    $ cd samples-and-tests/sample-app
    $ play deps --sync
    $ play run

Hit <http://localhost:9000> and see what happens.

