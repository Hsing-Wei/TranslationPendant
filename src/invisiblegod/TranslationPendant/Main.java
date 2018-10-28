/*
 * Copyright (C) 2014 神马才注册（Invisible God） <373575012@qq.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package invisiblegod.TranslationPendant;

import com.bkjzs.tp.util.net.BrowserLauncher;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Java翻译挂件 (TranslationPendant) <br/>
 * 老旧启动类
 *
 * @author 神马才注册
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //配置Swing
        configureSwing();
        /*
        JOptionPane.showMessageDialog(null,
                "此接口即将弃用，请尽快从README.TXT里的下载链接里下载最新版启动器！",
                "老旧版本警告", JOptionPane.WARNING_MESSAGE
        );
        BrowserLauncher.openURL("http://tieba.baidu.com/p/2459975331?see_lz=1");
        */
        com.bkjzs.tp.Main.main(args);
    }

    private static void configureSwing() {
        try {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            for (UIManager.LookAndFeelInfo info
                    : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOG.log(Level.SEVERE, "Swing error.", ex);
        }
    }
}
