# 2_manage_container

<a name="readme-top"></a>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-contents">About Contents</a></li>
    <li><a href="#h3-1-アプリケーションの実行">H3-1 アプリケーションの実行</a></li>
    <li><a href="#h3-2-アプリケーションの更新">H3-2 アプリケーションの更新</a></li>
  </ol>
</details>



<!-- ABOUT CONTENTS -->
## About Contents
このコンテンツはPodman入門ハンズオンの3つ目のコンテンツになります。
Podman を使って作成したアプリケーションを実行したり、コンテナ内のアプリケーションを更新するコンテナ開発の基礎を学びます。
このコンテンツで実施するタスクは以下の２つです

* **H3-1** アプリケーションの実行
* **H3-2** アプリケーションの更新

このコンテンツではPodmanを使ってアプリケーションを実行する方法を理解し、コンテナ開発ができるようになることが目的です。

※もしまだPodmanの準備ができていない場合は[Getting Started](../README.md#getting-started)を参照してください。


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- H3-1 -->
## H3-1 アプリケーションの実行

### **前提条件**

* [Getting Started](../README.md#getting-started)でセットアップが完了していること
* [2_manage_container](./2_manage_container/README.md#about-contents)のタスクを完了していること
* Podman machineが起動していること


### **ゴール**

* コンテナで任意のJavaアプリケーションを実行できる

### **Task**

1. SpringBootアプリケーションを作成する

    * materialsフォルダの説明
     
        このタスクではコンテナ上で動かすアプリケーションが必要になります。このREADME.mdを同じフォルダにmateralsというフォルダが配備されています。フォルダの中にはdemov1とdemov2という2つのGradleプロジェクトのSpringbootアプリケーションのコードが置いてあります。またjarというフォルダにはそれぞれのコードからビルドしたSpringBootアプリケーションのjarファイルがあるのを確認してください。

      <br/>

    * アプリケーションの概要

        [materials](./materials/)に配備されたアプリケーションはいずれもコンテナ上で動作するマイクロサービス作られたRestAPIのWebアプリケーションです。demov1は不完全な開発中のアプリ、demov2はdemov1に改修を加えたものを想定しています。コンテナアプリケーションの挙動を確認するためのものなので、Service層やDataAcess層は存在しておらず、ControllerだけのスタブAPIとなっています。インターフェースの簡略化とデモをわかりやすくするために Swaggerで　RestAPI のWebインターフェースを自動生成しています。

        demov1とdemov2の実行時の挙動はいずれもほぼ同じになります。例えばアプリケーションは下記URLでアクセス可能です。ただし同じPORTとパスが衝突するためdemov1とdemov2は同時に起動できません。
        
        http://localhost:8090/sample/swagger-ui/index.html#/

        2つアプリケーションの違いについては以下が目印になります

        * 上記のURLでアクセスした際にDemo-Rest-Appの右上にアプリケーションバージョンが表記されており、v1.0.0かv2.0.0で判別が可能
        * v2.0.0にのみAPIのインターフェースにPUTとDELETEが追加されている
        * v2.0.0のリクエストはJSON(RequestBody)に対応している
        
      <br/>

    * 開発環境について

        このハンズオンでは開発環境を用意する必要はありませんが、実際に開発環境を用意してアプリケーションを改修することも可能です。その場合に必要な構成を以下に示します。

        * JDK17 
        * Gradle 7.5 以上
        * VSCode (本体) 
        * Gradle for Java (拡張機能)
        * Lombok Annotations Support for VS Code (拡張機能)

      <br/>

    * 開発環境ができない場合

      アプリケーションのビルドができない場合は以下のフォルダにビルド済みの.jarを配置していますのでこちらを利用します。このタスクは飛ばして次の『Containerfileの作成』へ進んでください。

      <br/>    

    VSCodeを起動する

      1. クライアントにインストールしたVSCodeを実行します
      2. エクスプローラーのランチャーを右クリックで展開しワークスペースにフォルダーを追加で以下のフォルダを選択します

          ```path
          ./3_update_container/materials/demov1
          ```

    動作を確認する

      1. VSCodeのターミナルを開き `build.gradle` のある階層に移動します
      2. GradleのbootRunを実行します

          ```zsh
          % ./gradlew bootRun

          > Task :bootRun

            .   ____          _            __ _ _
           /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
          ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
          \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
           '  |____| .__|_| |_|_| |_\__, | / / / /
           =========|_|==============|___/=/_/_/_/
          :: Spring Boot ::                (v3.0.0)

          ...

          <==========---> 80% EXECUTING [2m 22s]
          > :bootRun
          ```
    
         bootRunが成功するとターミナルでプロセスが実行された状態になります。アプリケーションはこの状態で動作しています。アプリケーションを終了する場合はターミナルで `Ctrl + C`  を実行してプロセスを終了してください。
      
      3. bootRun実行中に以下のURLにブラウザでアクセスするとSwaggerのAPIインターフェースから動作を確認できます。

          http://localhost:8090/sample/swagger-ui/index.html#/
          

      <br/>

    コードを編集する

      1. 任意のコードに修正を加えます。
        
          * 特に指定のコードがないのであれば、`DemoApplication.java` の  `@OpenAPIDefinition` アノテーション内のversionを以下のように変更する

              ```java
              version = "v1.0.0"
              ↓
              version = "v1.1.0"
              ```
  
      <br/>

    ビルド実施する

      1. VSCodeのターミナルを開き `build.gradle` のある階層に移動します
      2. Gradleのビルドを実行します

          ```sh
          % ./gradlew build

          BUILD SUCCESSFUL in 2s
          7 actionable tasks: 4 executed, 3 up-to-date  
          ```
      3. ビルドが成功した場合、以下のフォルダに.jarが生成されます

          ```sh
          ./3_update_container/materials/demov1/build/libs/demo-0.0.1-SNAPSHOT.jar
          ```

     <br/>
     <br/>

2. Containerfileの作成
      
    * Containerfileについて

      Containerfile(Dockerfile)とは新規にコンテナイメージを作成するための手順を記したものです。あらかじめ用意されたイメージに対して追加の手順を加えてイメージを作成することができます。ここでは上記手順で作成したアプリケーション(jar)を実行する様に指示を加えたイメージを作成します。

    ベースとなるContainerfileをエディタ開き、demov1アプリを実行する様に編集します

      ```path
      vi ./3_update_container/materials/Containerfile
      ```

      コンテナファイルはOpenJDK用のものをベースにしており、JAR_FILEを追加し、アプリケーションを実行する処理を追加しています。コンテナファイルの中身は以下のようになっています。

      ```yaml
      # Change the image to match the machine's architecture.
      FROM arm64v8/openjdk:17-jdk
      # FROM amd64/openjdk:17-jdk 
      # If you use images provided by RedHat, please refer to the registry.
      # FROM registry.access.redhat.com/ubi8/openjdk-17:1.14-4.1666624568

      WORKDIR /workspace

      ARG JAR_FILE=./demov1/build/libs/*.jar
      # ARG JAR_FILE=./jar/v1/*.jar
      # ARG JAR_FILE=./jar/v2/*.jar

      COPY ${JAR_FILE} app.jar
      ENTRYPOINT ["java","-jar","app.jar"]
      ```

      以下の様に編集してください

      * アーキテクチャの変更

        FROMにはベースイメージがarm64で指定されています。ご利用の環境に応じてamd64用の`amd64/openjdk:17-jdk `またはマシンにあわせたアーキテクチャのOpenJDKコンテナをDocker.Hubで探して変更してください。RedHatのUBIを利用したい場合は`registry.access.redhat.com/ubi8/openjdk-17:1.14-4.1666624568`のように指定します。ご利用のマシンに応じて[RedHatEcosystemCatalog](https://catalog.redhat.com/)から取得してください。

        例:Amd64に変更する
        ```yaml
        # FROM arm64v8/openjdk:17-jdk
        FROM amd64/openjdk:17-jdk 
        ```

      * Jarファイルの位置を変更

        JAR_FILEの値は実行するjarのパスをさします。デフォルトではdemov1アプリのビルドターゲットを指しています。ビルド済みのアプリを指定する場合は以下のように編集してください。

        例: ビルド済みのdemov1でコンテナ作成する場合v1フォルダを指定
        ```yaml
        # ARG JAR_FILE=./demov1/build/libs/*.jar
        ARG JAR_FILE=./jar/v1/*.jar
        # ARG JAR_FILE=./jar/v2/*.jar
        ```
      編集が完了したらファイルを保存してください

     <br/>
     <br/>

3. イメージのビルド
    
    Containerfileのあるディレクトリに移動します

      ```sh
      cd ./3_update_container/materials 
      ```

    Podmanでイメージのビルドを実行します

      ```sh
      % podman build  -t demo/sample .  
      STEP 1/5: FROM arm64v8/openjdk:17-jdk
      Resolving "arm64v8/openjdk" using unqualified-search registries (/etc/containers/registries.conf.d/999-podman-machine.conf)
      Trying to pull docker.io/arm64v8/openjdk:17-jdk...

      ...

      Successfully tagged localhost/demo/sample:latest
      5413455bb57270c27549d31dcf2632ac64b7ae4f97014707e6230713cfe59793
      ```
      * `-t`はイメージのTag(名称)を指します。任意の名前をつけてください

    <br/>

    作成されたイメージを確認します

      ```sh
      % podman image list
      REPOSITORY                 TAG         IMAGE ID      CREATED       SIZE
      localhost/demo/sample      latest      5413455bb572  1 hours ago   530 MB
      docker.io/library/nginx    latest      835769b1adc0  8 days ago    139 MB
      quay.io/podman/hello       latest      316717fcf920  5 weeks ago   85.1 kB
      docker.io/arm64v8/openjdk  17-jdk      4717374ea615  1 hours ago  505 MB
      ``` 
      * `arm64v8/openjdk` がベースのイメージとしてpullされています
      * `demo/sample` のイメージが作成されました

      <br/>
      <br/>

4. コンテナの実行

    作成されたコンテナイメージからコンテナを実行します

    ```sh
    % podman run --name demo1 -p 8080:8090 demo/sample

      .   ____          _            __ _ _
    /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
    \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
    =========|_|==============|___/=/_/_/_/
    :: Spring Boot ::                (v3.0.0)

    2022-12-15T11:21:50.685Z  INFO 1 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication using Java 17.0.2 with PID 1 (/workspace/app.jar started by root in /workspace)

    ```
    * `-d` デタッチなしで実行します(次のタスクまで)
    * application.propertiesで指定した`server.port=8090` にむけてポートフォワードを指定します
    * アプリケーション単体でbootRunした結果と同じように標準出力に表示されています
    * PodmanDesktopで確認すると、ビルドしたイメージが有効化されており、demo1コンテナが動作しているのがわかります
    * さらにPodmanDesktopのContainersからdemo1コンテナの名称をクリックすると標準出力と同じログが確認できます

      <br/>
      <br/>

5. コンテナ上のアプリケーションを確認する

    以下のURLにアクセスしてください
    ```
    http://localhost:8080/sample/swagger-ui/index.html#/
    ```
    * PORTが8080指定している点に注目してください。ポートフォワードの指定が有効化されています。この手法をつかって複数のコンテナを同時に疎通させることが可能です
    * swaggerのWebインターフェースが確認できます。hello, create, getの3つのAPIが確認できます。アプリケーション名のバージョンはv1.0.0と表示されます(または`@OpenAPIDefinition`で指定したバージョン)

      <br/>

    確認が完了したら、ターミナルに戻って `Ctrl + C` でアプリを終了させてください


      <br/>
      <br/>

これでタスクは完了になります

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- H3-2 -->
## H3-2 アプリケーションの更新

### **前提条件**

* H3-1が完了しており、Podman machineが起動していること

### **ゴール**

* コンテナ内のアプリケーションを更新する

### **Task**

* **注意:このセクションはH3-1と同じ手順を繰り返します。詳細がわからなくなった場合はH3-1で確認してみてください**

<br/>

1. アプリのリビルド

    demov2のアプリケーションを展開するため、エクスプローラーのランチャーを右クリックで展開しワークスペースにフォルダーを追加で以下のフォルダを選択します

      ```path
      ./3_update_container/materials/demov2
      ```
      * demov2はdemov1をベースに、問題となっている点を一部修正しAPIを追加してたアプリケーションです。
      * 編集を行わずにビルドした場合 `@OpenAPIDefinition` に喜寿されているバージョンの表記はv2.0.0を指します

      <br/>
    
    任意の編集を実施してdemov2アプリケーションをビルドしてください

      ```sh
      % ./gradlew build

      BUILD SUCCESSFUL in 1s
      7 actionable tasks: 1 executed, 6 up-to-date
      ```

    デフォルトのビルドターゲットは以下になります
    
      ```sh
      ./materials/demov2/build/libs/*.jar
      ```

2. イメージのリビルド

    Containerfileでdemov2でビルドした.jarファイルを指定してください

      * Jarファイルの位置を変更

        JAR_FILEの値は実行するjarのパスをさします。デフォルトではdemov1アプリのビルドターゲットを指しています。ビルド済みのアプリを指定する場合は以下のように編集してください

        例: ビルド済みのdemov2でコンテナ作成する場合v2フォルダを指定
        ```yaml
        # ARG JAR_FILE=./demov1/build/libs/*.jar
        # ARG JAR_FILE=./jar/v1/*.jar
        ARG JAR_FILE=./jar/v2/*.jar
        ```
      <br/>

    編集が完了したらイメージをリビルドします

      ```sh
      % podman build -t demo/sample .
      STEP 1/5: FROM arm64v8/openjdk:17-jdk
      STEP 2/5: WORKDIR /workspace

      ...

      Successfully tagged localhost/demo/sample:latest
      8b37ac57b2e5b3507205acca97bb069be9bfc84e227ebe2cbd112c9ff343b6e0
      ``` 

      <br/>

    イメージが正しくできているかを確認します

      ```sh
      % podman image list                                
      REPOSITORY                 TAG         IMAGE ID      CREATED       SIZE
      localhost/demo/sample      latest      8b37ac57b2e5  2 hours ago   530 MB
      <none>                     <none>      5413455bb572  10 hours ago  530 MB
      docker.io/library/nginx    latest      835769b1adc0  9 days ago    139 MB
      quay.io/podman/hello       latest      316717fcf920  5 weeks ago   85.1 kB
      docker.io/arm64v8/openjdk  17-jdk      4717374ea615  7 months ago  505 MB
      ```
      * IMAGE ID がビルド結果で表示されたシリアルとプリフィックスが一致しています
      * いままで同じ名称だったものは`<none>`と表示される様になっています

    <br/>

    以前のイメージが不要となった場合は削除を実施します

      ```sh
      % podman image prune
      WARNING! This command removes all dangling images.
      Are you sure you want to continue? [y/N] y
      5413455bb57270c27549d31dcf2632ac64b7ae4f97014707e6230713cfe59793
      ```
      * `podman rmi <IMAGE ID>` で削除することも可能です
      * もし削除できない場合はコンテナが停止中で残っている可能性があります


3. コンテナの実行

    作成されたコンテナイメージからコンテナを実行します

    ```sh
    % podman run --name demo2 -p 8080:8090 demo/sample

      .   ____          _            __ _ _
    /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
    \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
    =========|_|==============|___/=/_/_/_/
    :: Spring Boot ::                (v3.0.0)

   2022-12-15T13:01:49.051Z  INFO 1 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication using Java 17.0.2 with PID 1 (/workspace/app.jar started by root in /workspace)
    ```
    * `-d` デタッチなしで実行します(次のタスクまで)

4. コンテナ上のアプリケーションを確認する

    以下のURLにアクセスしてください
    ```
    http://localhost:8080/sample/swagger-ui/index.html#/
    ```
    * H3-1で実行したコンテナは停止しているのでPORTは再利用できます
    * swaggerのWebインターフェースが確認できます。hello, create, get,update,deleteの5つにAPIが増えたのを確認できます。アプリケーション名のバージョンはv2.0.0と表示されます(または`@OpenAPIDefinition`で指定したバージョン)

      <br/>

    確認が完了したら、ターミナルに戻って `Ctrl + C` でアプリを終了させてください


      <br/>
      <br/>

これでタスクは完了になります


<p align="right">(<a href="#readme-top">back to top</a>)</p>


## 参考

none
