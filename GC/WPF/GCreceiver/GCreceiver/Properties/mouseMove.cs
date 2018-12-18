using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;
using System.Windows;

namespace GCreceiver
{

    class mouseMove
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

        public void moveAsController(double theta,double dist)
        {
            int horizon = (int)(Math.Cos(theta) * dist);
            int vertical = (int)(Math.Sin(theta) * dist);

            SetCursorPos(horizon, vertical);
        }
    }
}
