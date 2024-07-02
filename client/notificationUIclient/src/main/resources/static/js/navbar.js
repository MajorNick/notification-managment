const nav = document.querySelector('.navbar')
fetch('/home/majornick/Codes/spring/Task/client/notificationUIclient/src/main/resources/templates/navbar.html')
.then(res => res.text())
.then(data => {
    nav.innerHTML=data
})