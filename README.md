# flex-server

[![pipeline status](https://gitlab.com/boechat107-challenges/flexiana/badges/master/pipeline.svg)](https://gitlab.com/boechat107-challenges/flexiana/-/commits/master)

Flexiana's challenge test.

## Running

``` bash
clj -M:cljs -co ./repl_cljs.edn --compile
clj -M -m flex-server

curl -X GET http://localhost:3000/is-scrambled\?scrambled\=ajbc\&word\=abc
```

## Testing

``` bash
clj -M:test
```

## Development

Compile Clojurescript files and watch for changes:

``` bash
clj -M:cljs -co ./repl_cljs.edn -w src --compile
```

Start the web server:

``` bash
clj -M -m flex-server
```



## Known Issues - Improvements

* Development environment: Hot reloading (figwheel)
