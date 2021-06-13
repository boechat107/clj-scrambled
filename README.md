# flex-server

[![pipeline status](https://gitlab.com/boechat107-challenges/flexiana/badges/master/pipeline.svg)](https://gitlab.com/boechat107-challenges/flexiana/-/commits/master)

Flexiana's challenge test.

## Running

``` bash
clj -M -m flex-server

curl -X GET http://localhost:3000/is-scrambled\?scrambled\=ajbc\&word\=abc
```

## Testing

``` bash
clj -M:test
```

## Development

Compile Clojurescript files and open a REPL:

``` bash
clj -M:cljs
```
