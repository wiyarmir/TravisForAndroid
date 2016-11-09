#!/usr/bin/env bash
expect -c '
set timeout -1;
spawn android update sdk --no-ui --all --filter "extra-android-m2repository,extra-android-support,tools,platform-tools,build-tools-25.0.0,android-25,extra-google-m2repository,extra-google-google_play_services,sys-img-x86-android-23,addon-google_apis-google-23";
expect {
    "Do you accept the license" { exp_send "y\r" ; exp_continue }
    eof
}
'
