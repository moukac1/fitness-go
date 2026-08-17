//import { useAuthContext, AuthProvider, TAuthConfig, TRefreshTokenExpiredEvent } from "react-oauth2-code-pkce"

export const authConfig = {
  clientId: 'mouady-1',
  authorizationEndpoint: 'http://localhost:8075/realms/mouadyRealm/protocol/openid-connect/auth',
  tokenEndpoint: 'http://localhost:8075/realms/mouadyRealm/protocol/openid-connect/token',
  redirectUri: 'http://localhost:5173/',
  scope: 'openid',
  onRefreshTokenExpire: (event) => event.logIn(),
}

