# Install

## Maven
Make sure Maven is installed and running.
```
./mvnw --version

Apache Maven 3.9.7 (8b094c9513efc1b9ce2d952b3b9c8eaedaf8cbf0)
Maven home: /home/cdk2128/.m2/wrapper/dists/apache-maven-3.9.7/2a4cb831
Java version: 11.0.24, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.153.1-microsoft-standard-wsl2", arch: "amd64", family: "unix"
```

## Node
Install fnm or another node manager or install node manually
```
curl -fsSL https://fnm.vercel.app/install | bash

fnm
fnm install 22
```

## Install dxclient
```
npm install -g @hcl-software/dxclient

```

Run `dxclient --version`.

This will show you the dxclient version and create a new `store` folder which will be used as a configuration when running dxclient from the root folder of the project. Update the `./store/config.json` with the approproate values for your HCL DX server. See [config_sample.json](./config_sample.json) for an example.

Next, run version compatability to ensure the connection works:
```
dxclient version-compat

 DX Version: CF221
 Current DXClient Version: 222.0.0
 Recommended DXClient Version: 221.0.0
```

## Build and install the project
From the root of the project:

- Run the Maven build:

  ```
  ./mvnw clean install
  ```

- Deploy to your DX server:

  ```
  dxclient deploy-application -applicationFile ./ear/target/dxNotification.ear-0.0.1-SNAPSHOT.ear -applicationName dxNotification -dxProfileName wp_profile
  ```