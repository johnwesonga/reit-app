# reit-app

Basic [reitit](https://github.com/metosin/reitit) + [Integrant](https://github.com/weavejester/integrant) app


## Installation

Download from https://github.com/johnwesonga/reit-app.

## Usage

FIXME: explanation

Run the project using an alias configured in =deps.edn= file.

    $ clj -A:server

Run the project directly:

    $ clojure -m reit.reit-app

Now acces the app at: http://localhost:8080/.

Run the Application in REPL

     $ clj -A:dev

Once REPL starts, run the system:
  
     user=> (go)
  
Now acces the app at: http://localhost:8080/.

Run the project's tests (they'll fail until you edit them):

    $ clojure -A:test:runner

Build an uberjar:

    $ clojure -A:uberjar

Run that uberjar:

    $ java -jar reit-app.jar

## License

Copyright © 2020 Johnwesonga

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
