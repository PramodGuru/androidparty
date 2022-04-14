# androidparty

Current status
- Sends a POST request to http://playground.tesonet.lt/v1/tokens with the login credentials to get an auth token
- Using the auth token, a list of servers is fetched from the server via http://playground.tesonet.lt/v1/servers
- Basic error and HTTP handling
- Tools and framework used: Retrofit, Moshi, Koin (DI)
- Architecture: MVVM

Not implemented
- Unit tests
- Smooth animated transitions
- Persistant storage of data
- Exclusive loading page

Further improvements
- Reduce boilerplate and improve code reusage with Helpers and Providers
- Unit tests
- Save login data to bypass login page when a valid auth token is available
- Replace xml layouts with Compose
