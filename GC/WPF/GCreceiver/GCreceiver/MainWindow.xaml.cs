using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Runtime.InteropServices;

namespace GCreceiver
{
    /// <summary>
    /// MainWindow.xaml の相互作用ロジック
    /// </summary>
    public partial class MainWindow : Window
    {
        double valueOfSensibility;
        //double valueOfMouseX;
        //double valueOfMouseY;


        public MainWindow()
        {
            InitializeComponent();
            readLastSettings();
            mouseDataStore mValue = new mouseDataStore();
            mValue.mouseToCenter();
        }
        
        //sensibility value settings when snsValue changed
        private void sensValue_TextChanged(object sender, TextChangedEventArgs e)
        {
            String text = sensValue.Text;
            if (text!="" && double.TryParse(text, out this.valueOfSensibility))
            {
                sensSlider.Value = System.Math.Round(this.valueOfSensibility, 1);     //trans to the first decimal place
            }
        }


        /*****Functions for Button Event[from here]*****/
        private void quickResetLast_ButtonClick(object sender, RoutedEventArgs e)
        {
            readLastSettings();
        }

        private void saveSettings_ButtonClick(object sender, RoutedEventArgs e)
        {
            Properties.Settings.Default["Sensibility"] = this.valueOfSensibility;
            Properties.Settings.Default.Save();
        }

        private void startSocket_ButtonClick(object sender, RoutedEventArgs e)
        {
            int flag;
            new mouseMove();
            Console.WriteLine("Start Socket\n");
            do {
                flag=socket.socketCom();
                mouseMove.moveAsController();
                //mouseMove.moveTest();
            } while(flag==1);
        }
        /*****Functions for Button Event[here]******/

        /*****Read Values[from here]*****/

            //ここにsensSliderの値をチェック込みで返す関数

        //Reading last setting
        public void readLastSettings()
        {
            sensSlider.Value = (double)Properties.Settings.Default["Sensibility"];
        }

        //Reset to Default setting
        public void readDefaultSettings()
        {
            sensSlider.Value = (double)Properties.Settings.Default["defSensibility"];
        }
        /*****Read Values[here]*****/
    }
}
