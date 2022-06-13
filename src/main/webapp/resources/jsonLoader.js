$(document).ready(function () {
    $.getJSON("allgames",function (data) {
        let tbl = $("#tablebody");
        $.each(data,function (i,item) {
            let tr = $("<tr>");
            let td = $("<td>");
            td.append($("<a>",{
                'href':"games/game?gameId="+item.id,
                'text':item.name
            }));
            tr.append(td);
            tr.append($("<td>",{'text':item.developer}));
            tr.append($("<td>",{'text':item.year}));
            tr.append($("<td>",{'text':item.genre}));
            tr.append($("<td>",{'text':item.rank}));
            tbl.append(tr);
        })
    })
});