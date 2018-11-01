<pre>
execute following commands in order to create keystore files
(password always was ddg1234)

keytool -genkey -v -keystore my-privateKey.store -alias ddg -keyalg RSA -keysize 2048 -validity 10000
keytool -export -alias ddg -file my-certfile.cer -keystore my-privateKey.store (not so important)
keytool -import -alias publicDdg -file my-certfile.cer -keystore my-publicKey.store (not so important)

and gradle tasks choose: installWebstartDist , and .jnlp file with lib/*.jars will be generated.

and run :
javaws /home/edutilos/Desktop/tutorials/ControlFXTest/build/jnlp/launch.jnlp


//or create my-javaws.desktop in linux (in one of the following location)
1) /usr/share/applications  (for the current user)
2) ~/.local/share/applications/ (for all users)

with following content: 
[Desktop Entry]
Encoding=UTF-8
Name=Java 8 Web Start
Comment=Java 8 Web Start
Exec=/home/edutilos/Applications/jdk1.8.0_131/bin/javaws %u
Terminal=false
Type=Application
Icon=javaws
Categories=Application;Network;
MimeType=application/x-java-jnlp-file;


and create index.html with following content: 
&lt;!DOCTYPE html&gt;
&lt;html&gt;
&lt;head&gt;
    &lt;title&gt;run launch.jnlp&lt;/title&gt;
&lt;/head&gt;
&lt;body&gt;
&lt;a href="launch.jnlp"&gt;launch jnlp&lt;/a&gt;
&lt;/body&gt;
&lt;/html&gt;

and open this index.html in firefox (in chrome that does not work) . Firefox will give option to 
run with (and choose "Java 8 Web Start")

</pre>
