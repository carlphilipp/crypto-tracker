export function formatDate(unixTime) {
    // FIXME is that really what needs to be done to obtain the date?
    var date = new Date(unixTime*1000);
    var hours = date.getHours();
    var minutes = "0" + date.getMinutes();
    var seconds = "0" + date.getSeconds();
    var formattedTime = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    return formattedTime
}
