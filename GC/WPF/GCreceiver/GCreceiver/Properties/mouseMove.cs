using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;
using System.Windows;

namespace GCreceiver
{

    class mouseMove : socket
    {
        static double height;
        static double width;
        static int heightCenter;
        static int widthCenter;

        public mouseMove()
        {
            height = SystemParameters.PrimaryScreenHeight;
            width = SystemParameters.PrimaryScreenWidth;
            heightCenter = (int)height / 2;
            widthCenter = (int)width / 2;
            Console.WriteLine(height + "aaaaa" +width);
        }

        [DllImport("USER32.dll", CallingConvention = CallingConvention.StdCall)]
        static extern void SetCursorPos(int X, int Y);
        [DllImport("USER32.dll", CallingConvention = CallingConvention.StdCall)]
        static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);


        public static void moveTest()
        {
            SetCursorPos(widthCenter, heightCenter);
        }

        public static void moveAsController()
        {
            
            int horizon = (int)((Math.Cos(socket.getTheta()) * socket.getDist())*1000);
            int vertical = (int)((Math.Sin(socket.getTheta()) * socket.getDist())*1000);
            Console.WriteLine("moveAsController called");
            Console.WriteLine("theta:"+socket.getTheta() + " dist:" + socket.getDist());

            Console.WriteLine("horizon:"+horizon +"vertical:"+vertical);

            SetCursorPos(widthCenter + vertical, heightCenter + horizon);
        }

        
    }
}




