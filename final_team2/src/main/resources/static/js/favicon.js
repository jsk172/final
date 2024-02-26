/**
 * 
 */

 function loadFavicon(){
    var link = document.createElement('link');
    link.rel = 'icon';
    link.href = '/images/favicon/logo_icon_96x96.png'; //favicon의 URL을 지정
    link.type = 'image/png'; //favicon의 MIME 타입을 지정(파일유형 저장)
    document.head.appendChild(link);
 }
 
 window.onload = loadFavicon;