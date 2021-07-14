# clj-scrambled

![scrambled test status](https://github.com/boechat107/clj-scrambled/actions/workflows/test.yml/badge.svg)

A simple web application for checking if one string could be
rearranged into another.

![screenshot](./screenshot.png)

## Requirements

* JVM (tested with openjdk 11)
* Clojure [command-line tools](https://clojure.org/guides/deps_and_cli)

## Running

``` bash
clj -M:cljs -co ./cljs_prod.edn --compile
clj -M -m scrambled-server [port]
```

## Testing

``` bash
clj -M:test
```

## Development

Compile Clojurescript files and watch for changes:

``` bash
clj -M:cljs -co ./cljs_dev.edn -w src --compile
```

Start the web server:

``` bash
clj -M -m scrambled-server [port]
```


## Known Issues - Improvements

* Improve the Cljs development environment with hot-reloading (figwheel)
* Use browser automation (or some [jsdom](https://github.com/jsdom/jsdom))
to test the front-end working with the server.
* Uberjar generation for deployment.
