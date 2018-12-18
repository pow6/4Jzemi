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
        double height;
        double width;
        int heightCenter;
        int widthCenter;


        public mouseMove()
        {
            height = SystemParameters.PrimaryScreenHeight;
            width = SystemParameters.PrimaryScreenWidth;
            heightCenter = (int)height / 2;
            widthCenter = (int)width / 2;
        }

        [DllImport("USER32.dll", CallingConvention = CallingConvention.StdCall)]
        static extern void SetCursorPos(int X, int Y);
        [DllImport("USER32.dll", CallingConvention = CallingConvention.StdCall)]
        static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);


        public static void moveTest()
        {
            SetCursorPos(120, 120);
        }

        public static void moveAsController()
        {
            
            int horizon = (int)(Math.Cos(socket.getTheta()) * socket.getDist());
            int vertical = (int)(Math.Sin(socket.getTheta()) * socket.getDist());

            SetCursorPos(horizon, vertical);
        }

        
    }
}
