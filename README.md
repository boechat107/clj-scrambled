# flex-server

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
