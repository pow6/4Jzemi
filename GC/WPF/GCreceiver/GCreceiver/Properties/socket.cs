using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GCreceiver
{
    class socket
    {
        static double thetaFromAndroid=100;
        static double distFromAndroid=10;

        public static int socketCom()
        {
            //ListenするIPの指定
            string IP_String = "0.0.0.0";
            System.Net.IPAddress IP_Address = System.Net.IPAddress.Parse(IP_String);

            //Listenするport番号
            int PORT = 5000;

            //TcpListenerオブジェクトの作成
            System.Net.Sockets.TcpListener listener = new System.Net.Sockets.TcpListener(IP_Address, PORT);

            //Listenを開始
            listener.Start();
            Console.WriteLine("Listenを開始しました({0}:{1})", ((System.Net.IPEndPoint)listener.LocalEndpoint).Address,((System.Net.IPEndPoint)listener.LocalEndpoint).Port);

            //接続要求のチェック，受け入れ
            System.Net.Sockets.TcpClient client = listener.AcceptTcpClient();
            Console.WriteLine("クライアント({0}:{1})と接続しました。",
                ((System.Net.IPEndPoint)client.Client.RemoteEndPoint).Address,
                ((System.Net.IPEndPoint)client.Client.RemoteEndPoint).Port);
            //NetworkStreamを取得
            System.Net.Sockets.NetworkStream ns = client.GetStream();

            //読み取り、書き込みのタイムアウトを10秒にする
            //デフォルトはInfiniteで、タイムアウトしない
            //(.NET Framework 2.0以上が必要)
            ns.ReadTimeout = 10000;
            ns.WriteTimeout = 10000;

            //クライアントから送られたデータを受信する
            System.Text.Encoding enc = System.Text.Encoding.UTF8;
            bool disconnected = false;
            System.IO.MemoryStream ms = new System.IO.MemoryStream();
            byte[] resBytes = new byte[256];
            int resSize = 0;

            StreamReader sr = new StreamReader(ns, enc);
            string resMsg = String.Empty;
            string[] values = new string[3];
            do
            {
                resMsg = sr.ReadLine();
                if (resMsg == null) break;
                Console.WriteLine(resMsg);

                values = resMsg.Split(' ');
                switch (values[0])
                {
                    case "theta:":
                        thetaFromAndroid = double.Parse(values[1]);
                        break;
                    case "dist:":
                        distFromAndroid = double.Parse(values[1]);
                        break;
                    default:
                        break;
                }
                Console.WriteLine(values[0]);
                Console.WriteLine(values[1]);


                //thetaFromAndroid = 100;
                //distFromAndroid = 10;
                /*
                //データの一部を受信する
                resSize = ns.Read(resBytes, 0, resBytes.Length);
                //Readが0を返した時(ソケットが閉じられている場合)はクライアントが切断したと判断
                if (resSize == 0)
                {
                    disconnected = true;
                    Console.WriteLine("クライアントが切断しました。");
                    break;
                }
                


                //受信したデータを蓄積する
                ms.Write(resBytes, 0, resSize);

                //まだ読み取れるデータがあるか、データの最後が\nでない時は、
                // 受信を続ける
            } while (ns.DataAvailable || resBytes[resSize - 1] != '\n');
            */
            } while (true);


            //受信したデータを文字列に変換
            resMsg = enc.GetString(ms.GetBuffer(), 0, (int)ms.Length);
            //末尾の\nを削除
            resMsg = resMsg.TrimEnd('\n');
            Console.WriteLine(resMsg);
            //閉じる
            ms.Close();
            ns.Close();
            client.Close();
            Console.WriteLine("クライアントとの接続を閉じました。");

            //リスナを閉じる
            listener.Stop();
            Console.WriteLine("Listenerを閉じました。");

            Console.ReadLine();
            return 1;   //規定文字が来たら，-1などを返すようにあとで変える
        }


        public static double getTheta()
        {
            return socket.thetaFromAndroid;
        }
        public static double getDist()
        {
            return socket.distFromAndroid;
        }

    }
}
