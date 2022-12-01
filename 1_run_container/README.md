# 1_run_container

<a name="readme-top"></a>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-contents">About Contents</a>
    </li>
    <li>
      <a href="#Task-and-steps">Task And Steps</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT CONTENTS -->
## About Contents

最初のセクションになります。


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Task And Steps

このデモ/ハンズオンはユーザがPodmanの基礎を理解するためのものです。ローカル環境でコンテナ、Podを動作させ、Podのライフサイクルを理解しkubernetes環境への展開を準備することを目的としています。
以降のドキュメントでは対象の環境をRHELとして説明しますが、WindowsおよびMacでも同じように動かすことができます。

まずは、前提条件を理解し、環境の準備を行なってください。


### Prerequisites

1. 前提条件としてユーザが以下の環境及びスキルを備えていること
    * コンテナに関する前提知識を学習していること
    * Podmanに関する前提知識を学習していること(事前にワークショップにて座学を実施します)
    * コマンドラインインターフェースでの操作ができること
    * Podman/PodmanDesktopをインストール可能なクライアント(RHEL/OtherLinux/Windows/Mac)を準備する
    * クライアントからインターネットに接続可能であること
    * Gitを利用可能であること

2. また、下記の注意事項は各位のクライアントによる環境差異が起きる可能性を示します
    * ローカルの仮想環境やDockerにてportが利用されていないこと
    * 既にPodmanまたはPodmanDesktopを利用していないことを確認
  
      ```sh
      podman -v
      ```



### Installation

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



<!-- USAGE EXAMPLES -->
## Usage

チェックアウトした教材フォルダを参照します。フォルダにはプリフィックス番号があり、それに従ってコンテンツを進行します。詳細はそれぞれ子のフォルダにあるREADMEを参照してください。

コンテンツリスト
* 開発者向け
  1. podmanを使ったコンテナの実行 [1_run_container]
  2. podmanを使ったコンテナの管理 [2_manage_container]
  3. podmanを使ったDockerComposeからのPod作成 [3_convert_pod]

* 運用者向け
  1. 準備中

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Contents Creation
    - [x] For Developer
    - [ ] For Operator
- [ ] Run Your Pod on k8s/OpenShift

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b ***/***`)
3. Commit your Changes (`git commit -m 'Add some ***'`)
4. Push to the Branch (`git push origin ***/***`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the Apache-2.0 License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Tomoki Omori -  toomori@redhat.com

Project Link: None

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[podman.io]: https://img.shields.io/badge/Podman-892CA0?logo=podman&style=for-the-badge
[podman-url]: https://podman.io/
[spring.io]: https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white
[spring-url]: https://spring.io/projects/spring-boot
[wp.org]: https://img.shields.io/badge/Wordpress-21759B.svg?logo=wordpress&style=for-the-badge
[wp-url]: hhttps://ja.wordpress.org/



