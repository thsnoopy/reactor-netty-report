# A test app for reactor-netty issue report

This project demonstrates reactor-netty `Connection prematurely closed BEFORE response` issue.

## Build & Test
Demonstration is composed of three apps :
- server-actor : Provides `POST` API endpoint
- client-actor : A client that calls `POST` API using `WebClient`
- test-runner : Just a trigger app that `client-actor` communicates with `server-actor`

`test-runner` --(GET)-->  `client-actor` --(POST)--> `server-actor`

To build&run, just run `runTest.sh` in your console.
```
./runTest.sh
``` 

If the test result failed, see the log file in `client-actor/log/server-actor-***.log`.

## Run results
With 20 percent probability, `client-actor` failed with `Connection prematurely closed BEFORE response` error.
