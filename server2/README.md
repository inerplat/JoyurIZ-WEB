# Joyuriz-springboot

## Docker run
- Mount image storage
- Mount ssl cert key
- Port forward 1029:443
- Use environment variable file
```shell
sudo docker run -it \ 
  -v /home/ubuntu/uploads:/run/uploads \
  -v /home/ubuntu/cert:/run/cert \
  -p 1029:443 \
  --env-file=config.env \
  inerplat/joyuriz-springboot
```

## Environment variable
 - Usage:
   - DB connection information
   - ML API information
   - Web client information (for cross-origin allow)
   - jks key store password

```shell
SPRING_PROFILES_ACTIVE=prod

DB_URL=
DB_USER=
DB_PASSWORD=

API_IP=
API_PORT=
API_METHOD=

WEB_IP=
WEB_PORT=

KEY_STORE_PASSWORD=
```

## openssl to jks

```Shell
sudo openssl pkcs12 -export -in cert.pem -inkey privkey.pem -out joyuriz.p12 -name
 joyuriz -CAfile chain.pem -caname root

keytool -importkeystore -deststorepass [KEY] -destkeypass [KEY] -destkeystore joyuriz.jks -srckeystore joyuriz.p12 -srcstoretype PKCS12 -srcstorepass [KEY]
```

