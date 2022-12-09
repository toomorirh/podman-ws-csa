# 1_run_container

<a name="readme-top"></a>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-contents">About Contents</a></li>
    <li><a href="#h1-1-podman-machineの起動と確認">H1-1 Podman Machine起動</a></li>
    <li><a href="#h1-2-podmanをつかってコンテナを実行し動作を確認する">H1-2 Podmanをつかってコンテナを実行し動作を確認する</a></li>
  </ol>
</details>



<!-- ABOUT CONTENTS -->
## About Contents
このコンテンツはPodman入門ハンズオンの最初のコンテンツになります。
このコンテンツで実施するタスクは以下の２つです。
* **H1-1** Podman machineの起動と確認
* **H1-2** Podmanをつかってコンテナを実行し動作を確認する

このコンテンツではPodmanをつかってコンテナを動かすことができる様になることが目的です。

※もしまだPodmanの準備ができていない場合は[Getting Started](../README.md#getting-started)を参照してください。


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- H1-1 -->
## H1-1 podman machineの起動と確認

### **前提条件**

* [Getting Started](../README.md#getting-started)でセットアップが完了していること
* クライアントにPodmanがインストールされていること

### **ゴール**

* podman machineが起動している状態にする

### **Task**

1. Podmanのバージョンを確認する(Ver4.3.0以上であること)

      ```sh
      % podman -v
      podman version 4.3.1
      ```

      <br/>
      <br/>

2. Podman machineの初期化を行う(Getting Startedで実施済みなら無視)

      ```sh
      % podman machine init
      Downloading VM image: fedora-coreos-36.20220723.2.2-qemu.aarch64.qcow2.xz: done
      Extracting compressed file
      ```
      * イメージは初回のみダウンロード実施されます
      * 場合によってはイメージの更新が発生することがあります
      * ダウンロードされるマシンはアーキテクチャによって異なるので上記の結果どおりにならない場合があります。  
      * イメージはfedora-coreosがベースになります

      <br/>
      <br/>

3. Podman machineを起動します
      ```sh
      % podman machine start
      Starting machine "podman-machine-default"
      Waiting for VM ...
      Mounting volume...

        ...

      Machine "podman-machine-default" started successfully
      ```
      起動したら動作していることを以下のコマンドで確認します
      ```sh
      % podman machine ls
      NAME                     VM TYPE     CREATED      LAST UP            CPUS        MEMORY      DISK SIZE
      podman-machine-default*  qemu        5 weeks ago  Currently running  1           2.147GB     107.4GB
      ```
      * Currently running が表示されていれば成功です
      * Podman machineは　`podman machine start`　で起動　`podman machine stop` で停止させることができます
      * podman desktopで作成した場合デフォルト名`podman-machine-default*`が付与されています

      <br/>
      <br/>
  
これでタスクは完了になります

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- H1-1 -->
## H1-2 Podmanをつかってコンテナを実行し動作を確認する

### **前提条件**

* H1-1が完了しており、Podman machineが起動していること

### **ゴール**

* podmanからコンテナを実行できる

### **Task**

1. コンテナを実行する(nginx)

    ```sh
    % podman run --name ngx -d -p 8080:80 nginx:latest
    Resolving "nginx" using unqualified-search registries (/etc/containers/registries.conf.d/999-podman-machine.conf)
    Trying to pull docker.io/library/nginx:latest...
    Getting image source signatures
    Copying blob sha256:01c24b0718bb5ce60eb8410b6d200d8475939274aec7b62ba076de26c93821c7
    Copying blob sha256:6064e7e5b6afa4dc711228eddfd250aebac271830dc184c400ce640020bc2cb0
    Copying blob sha256:7f282f87664a1fe35cd3bff217af241b36977e37337ca16cb99799186caa46f1

    ...

    ```
    * `podman run` でコンテナを実行しています
    * docker.ioからnginxイメージを取得しています
    * `-d` オプションによりデタッチモードになります

    <br/>
    <br/>


2. コンテナの実行を確認する
    ```sh
    % podman ps 
    CONTAINER ID  IMAGE                           COMMAND               CREATED      STATUS          PORTS                 NAMES
    daf3ecd473a7  docker.io/library/nginx:latest  nginx -g daemon o...  3 hours ago  Up 3 hours ago  0.0.0.0:8080->80/tcp  ngx
    ```
    * `podman ps`で実行中のコンテナを確認できます
    * 先ほどコンテナを起動した際の設定が反映されていることが確認できます
    * PodmanDesktopを開きContainersの項目を参照してください。名前で検索すると実行中のコンテナが確認できます


3. コンテナのアプリケーション(nginx)の実行を確認する
    
    ブラウザから http://localhost:8080/ へアクセスしてnginxが動いていることを確認します

    * 8080ポートからコンテナの80ポートへフォワードしています
    
    コンテナを停止させます
    ```sh
    % podman stop ngx
    ngx
    % podman ps      
    CONTAINER ID  IMAGE       COMMAND     CREATED     STATUS      PORTS       NAMES
    ```
    ブラウザから http://localhost:8080/ へアクセスしてnginxが停止していることを確認します

      <br/>
      <br/>

これでタスクは完了になります


<p align="right">(<a href="#readme-top">back to top</a>)</p>


## 参考

1. [【podman machine】macOS上でPodmanを実行する新コマンドの紹介](https://rheb.hatenablog.com/entry/podman-machine)



