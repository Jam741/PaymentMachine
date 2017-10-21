/**
 * Created by xulu on 2017/8/16.
 * 用于 IE11
 */
var filepath = 'C://cashLog.txt';
//读文件
function readFile(){
    var fso = new ActiveXObject("Scripting.FileSystemObject");
    var f = fso.OpenTextFile(filepath,1);
    var s = "";
    while (!f.AtEndOfStream)
        s += f.ReadLine()+"/n";
    f.Close();
    return s;
}

//写文件
function writeFile(num){
    var fso, f, s ;
    fso = new ActiveXObject("Scripting.FileSystemObject");
    if(fso.FileExists(filepath)){
        console.log(fso.GetFile(filepath).size)
        if(fso.GetFile(filepath).size>1024){
            f = fso.OpenTextFile(filepath,2,true);
            f.writeLine(new Date()+'==>'+num);
            f.close();
        }
    }
    f = fso.OpenTextFile(filepath,8,true);
    f.WriteLine('==>'+new Date());
    f.Close();
}
