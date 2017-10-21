/**
 * Created by caobin on 2017/8/16.
 */
function doSave(value, type, name) {
    var blob;
    if (typeof window.Blob == "function") {
        blob = new Blob([value], {type: type});
    } else {
        var BlobBuilder = window.BlobBuilder || window.MozBlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder;
        var bb = new BlobBuilder();
        bb.append(value);
        blob = bb.getBlob(type);
    }
    var URL = window.URL || window.webkitURL;
    var bloburl = URL.createObjectURL(blob);
    var anchor = document.createElement("a");
    if ('download' in anchor) {
        anchor.style.visibility = "hidden";
        anchor.href = bloburl;
        anchor.download = name;
        document.body.appendChild(anchor);
        var evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, true);
        anchor.dispatchEvent(evt);
        document.body.removeChild(anchor);
    } else if (navigator.msSaveBlob) {
        navigator.msSaveBlob(blob, name);
    } else {
        location.href = bloburl;
    }
}

var a=new Array();
for(var i=0;i<10;i++)
    a.push(i);

function Save(){
    doSave(a, "text/latex", "hello.txt");
}
//=====================================
function SaveWrite() {
    var filepath = 'D://js.txt';
    var fso = new ActiveXObject('Scripting.FileSystemObject');
    if(fso.FileExists(filepath)){
        if(fso.GetFile(filepath).size>1024){
            //重写
            var f = fso.createtextfile(filepath,8,true);
            f.writeLine('==>'+new Date());
            f.close();
        }else{
            var f=fso.opentextfile(filepath,1,true);
            var content='';
             content += f.ReadLine();
            alert(content);
            var fnew = fso.createtextfile(filepath,8,true);
            fnew.writeLine(content+'==>'+new Date());
            fnew.close();
            f.close();
        }
    }else{
        //重写
        var f = fso.createtextfile(filepath,8,true);
        f.writeLine('==>'+new Date());
        f.close();
    }


    // var f=fso.opentextfile('D://js.txt',1,true);
    // console.log(f.size)
    // var f = fso.createtextfile('D://js.txt',8,true);
    // f.writeLine('==>'+new Date());
    // f.close();
}


//========================================

function savefile( f ) {
    f = f.elements;  //  reduce overhead
    var w = window.frames.w;
    if( !w ) {
        w = document.createElement( 'iframe' );
        w.id = 'w';
        w.style.display = 'none';
        document.body.insertBefore( w, null );
        w = window.frames.w;
        if( !w ) {
            w = window.open( '', '_temp', 'width=100,height=100' );
            if( !w ) {
                window.alert( 'Sorry, the file could not be created.' ); return false;
            }
        }
    }
    var d = w.document,
        ext = f.ext.options[f.ext.selectedIndex],
        name = f.filename.value.replace( /\//g, '\\' ) + ext.text;
    d.open( 'text/plain', 'replace' );
    d.charset = ext.value;
    if( ext.text==='.txt' ) {
        d.write( f.txt.value );
        d.close();
    } else {  //  '.html'
        d.close();
        d.body.innerHTML = '\r\n' + f.txt.value + '\r\n';
    }
    if( d.execCommand( 'SaveAs', null, name ) ){
        window.alert( name + ' has been saved.' );
    } else {
        window.alert( 'The file has not been saved.\nIs there a problem?' );
    }
    w.close();
    return false;  //  don't submit the form
}
