# Structured Standup

Rewrite of [structured-standup](https://github.com/soziflip/structured-standup)
based on [frontroute-example](https://github.com/yurique/frontroute-example).

## Description

This is a tiny helper to help a developer team to structure their Daily Standup Meeting by suggesting an order of the team member's turns.

I picked this use case to get my hands on the Functional Reactive Scala.js UI library [Laminar](https://laminar.dev/documentation#modifiers).

## Running

Install npm dependencies:

```
yarn install
```

Build the front-end:

```
sbt frontendJS/fastLinkJS
```

Start the webpack dev server:

```
yarn start
```

## Open in a browser

Open http://localhost:30290/ in the browser.

## Developing

To make sbt re-compile the front-end on code changes:

```
sbt ~frontendJS/fastLinkJS
```

## Production build

Build an optimized JS:

```
sbt frontendJS/fullLinkJS
```

Run webpack:

```
yarn run build
```

The front-end assets will be generated into the `dist` folder.

## Authors

- Philipp Moers – [@soziflip](https://twitter.com/soziflip)
- Armin Grodon – [x4121](https://github.com/armingrodon)


## License

This software is provided under the [MIT license](LICENSE.md).
