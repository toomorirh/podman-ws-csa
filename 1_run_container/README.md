# 1_run_container

<a name="readme-top"></a>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-contents">About Contents</a></li>
    <li><a href="#h1-1-podman-machineの起動と確認">H1-1 Podman Machine起動</a></li>

  </ol>
</details>



<!-- ABOUT CONTENTS -->
## About Contents
このコンテンツはPodman入門ハンズオンの最初のコンテンツになります。
このコンテンツで実施するタスクは以下の２つです。
* **H1-1** Podman machineの起動と確認
* **H1-2** Podman をつかってコンテナを実行し動作を確認する

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

### Task

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
  
これでタスクは完了です。

<p align="right">(<a href="#readme-top">back to top</a>)</p>



## H1-2 Installation

Podmanのインストールを実施します。また必要に応じてGitからドキュメントや教材をダウンロードする必要があるためGitも準備しておきます

1. Podmanの準備
  * GUI環境(Windows/Mac/RHEL)を利用する場合、 
    [https://podman-desktop.io](https://podman-desktop.io/) からクライアントに対応したpodman desktopをダウンロードしてインストールしてください。
    
    podman desktopを起動すると、podmanのインストールを聞かれますので

  * CUI環境(AWS RHEL)を利用する場合はyumからpodmanをインストールします
    ```sh
     yum install podman
    ```

2. Gitの準備
  * クローンするだけですのでGitHubのアカウントは不要です
    ```sh
    yum install git
    ```
3. 教材の取得 
    ```sh
    git clone https://github.com/toomorirh/podman-ws-csa.git
    ```
4. バージョンの確認
  * 4.2以上を用意してください
    ```sh
    podman version 4.3.0
    ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## 参考

[【podman machine】macOS上でPodmanを実行する新コマンドの紹介](https://rheb.hatenablog.com/entry/podman-machine)



