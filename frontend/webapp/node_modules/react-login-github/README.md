This is a fork of [react-github-login](https://github.com/checkr/react-github-login) since the project seems to be abandoned. This fork includes:

- Updated React > 16.0.0 and all other dependencies.
- Popup custom position.
- Popup auto-center by default.
- Disable button property.

# React Login with Github

![NPM](https://img.shields.io/npm/v/react-login-github.svg?style=flat)
![CircleCI](https://circleci.com/gh/rlamana/react-login-github.svg?style=shield&circle-token=493b950057f69e68ac8698a9ee189b2132a296e4)

React component for [GitHub Login](https://developer.github.com/v3/oauth/).

## Usage

The easiest way to use react-login-github is to install it from npm and build it into your app with webpack:

```
yarn add react-login-github
```

or using npm:

```
npm install react-login-github --save
```

Then use it in your app:


```js
import React from 'react';
import ReactDOM from 'react-dom';
import LoginGithub from 'react-login-github';

const onSuccess = response => console.log(response);
const onFailure = response => console.error(response);

ReactDOM.render(
  <LoginGithub clientId="ac56fad434a3a3c1561e"
    onSuccess={onSuccess}
    onFailure={onFailure}/>,
  document.getElementById('example')
);
```

### Props

#### `clientId`

`{string}` _required_

Client ID for GitHub OAuth application.

#### `redirectUri`

`{string}`

Registered redirect URI for GitHub OAuth application.

#### `scope`

`{string}`

Scope for GitHub OAuth application. Defaults to `user:email`.

#### `className`

`{string}`

CSS class for the login button.

#### `buttonText`

`{string}`

Text content for the login button.

#### `onRequest`

`{function}`

Callback for every request.

#### `onSuccess`

`{function}`

Callback for successful login. An object will be passed as an argument to the callback, e.g. `{ "code": "..." }`.

#### `onFailure`

`{function}`

Callback for errors raised during login.

#### `disabled`

`{bool}`

Disable login button.


## Development

```sh
$ npm start
```
