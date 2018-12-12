using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace GCreceiver
{
    class mouseDataStore
    {
        //display resolution values
        int screenHeight = 1080;
        int screenWedth = 1920;
        //moving mouse values
        [DllImport("USER32.dll", CallingConvention = CallingConvention.StdCall)]
        public static extern void SetCursorPos(int X, int Y);
        [DllImport("USER32.dll", CallingConvention = CallingConvention.StdCall)]
        public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

        public void mouseToCenter()
        {
            SetCursorPos(screenWedth/2,screenHeight/2);
        }
    }
}
