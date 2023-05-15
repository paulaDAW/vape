var hoy = new Date().toISOString().split('T')[0];
document.getElementsByName("fecha")[0].setAttribute('minDate', hoy);
console.log(new Date().toISOString());
var maxDate = new Date(new Date().getTime() + 60*24*60*60*1000).toISOString().split('T')[0];
document.getElementsByName("fecha")[0].setAttribute('maxDate', maxDate);

