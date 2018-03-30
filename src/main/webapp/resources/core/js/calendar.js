/**
 * Created by Nurzhan on 14.07.2017.
 */
function getDateInID(id) {
    var _date = document.getElementById(id).value;
    if (_date == "" || _date == null) {
        _date = dateFormatJavaScript(new Date().toLocaleDateString('ru-RU'));
        document.getElementById(id).value = _date;
    }
    if (_date.length == 10) {
        document.getElementById(id+"Table").innerHTML = getCalendarOnlyJS(id, _date);
        document.getElementById(id).value = _date;
    }
    document.getElementById(id+'Edit').style.display = 'block';
}
function getCalendarOnlyJS(id, date) {
    var format = '';
    format = getLocalFormat(date);
    if (format == 'js') {
        return getCalendarHtmlCode(date, id)
    }
    return getCalendarHtmlCodeDot(date, id);
}

function getCalendarHtmlCode(Selected, id) {
    var day = Selected.substring(8, 10);
    var month = Selected.substring(5, 7);
    var year = Selected.substring(0, 4);
    var table_html_code = dateInMonthMethod(day, month, year, id);
    return table_html_code.all_date_table;
}
function getCalendarHtmlCodeDot(Selected, id) {
    var day = Selected.substring(0, 2);
    var month = Selected.substring(3, 5);
    var year = Selected.substring(6, 10);
    var table_html_code = dateInMonthMethod(day, month, year, id);
    return table_html_code.all_date_table;
}
function dateInMonthMethod(day_curr_date, month_curr_date, year_curr_date, id_element) {
    var all_date = {all_days: [], all_year: [], all_date_table: ""};
    var D1last = new Date(year_curr_date, month_curr_date, 0).getDate();
    all_date.all_days = [];
    for (var i = 0; i < D1last; i++) {
        var all_day = i + 1;
        if (all_day < 10) all_day = "0" + all_day;
        all_date.all_days.push(all_day);
    }
    all_date.all_year = [];
    for (var i = year_curr_date * 1 - 10; i < year_curr_date * 1 + 10; i++) {
        all_date.all_year.push(i);
    }
    if (D1last < day_curr_date) day_curr_date = D1last;
//        this.current_risk.date = year_curr_date + "-" + month_curr_date + "-" + day_curr_date;
    var D1Nfirst = new Date(year_curr_date, month_curr_date - 1, 1).getDay();
    var D1Nlast = new Date(year_curr_date, month_curr_date - 1, D1last).getDay();
    var days_of_week = ["воскресенье", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"];
    var month_of_year = ["январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"];
    var table_start = "<table class='table table-striped table-bordered' style='margin-bottom: 0px;'><tr>";
    var table_day = "";
    var table_editer = "<table class='table' style='margin-bottom: 0px'><tr>" +
        "<td class='risk-table-light' onclick=\"getDateEditDotSeparate('"+ id_element +"', 'monthMinus')\"><</td>" +
        "<td>Месяц</td>" +
        "<td class='risk-table-light' onclick=\"getDateEditDotSeparate('"+ id_element +"', 'monthPlus')\">></td>" +
        "<td class='risk-table-light' onclick=\"getDateEditDotSeparate('"+ id_element +"', 'yearMinus')\"><</td>" +
        "<td>Год</td>" +
        "<td class='risk-table-light' onclick=\"getDateEditDotSeparate('"+ id_element +"', 'yearPlus')\">></td>" +
        "<td class='risk-table-light' onclick=\"getDateEditDotSeparate('"+ id_element +"', 'dateDel')\">X</td>" +
        "</table></tr>";
    var table_header = "<tr><td colspan='4'>" + month_of_year[month_curr_date - 1].toUpperCase() + "</td>" +
        "<td colspan='3'>" + year_curr_date + "</td></tr>" +
        "<tr><td>ПН</td><td>ВТ</td><td>СР</td><td>ЧТ</td><td>ПТ</td><td>СБ</td><td>ВС</td></tr>";
    var table_day_week = D1Nfirst - 1;
    var table_end = "</table>";
    for (var i = 1; i < D1Nfirst; i++) {
        table_day += "<td>";
    }
    if (D1Nfirst == 0) table_day += "<td></td><td></td><td></td><td></td><td></td><td></td>";
    for (var i = 0; i < D1last; i++) {
        if (table_day_week == 0) table_day += "<tr>";
        if (i + 1 == day_curr_date)
            table_day += "<td align='center' onclick='getDayClickButton(" + (i + 1) + "," + month_curr_date + "," + year_curr_date + "," + id_element + ")' style='background: #5e6ad0; color: white; cursor: default'>" + (i + 1);
        /*serverdate*/
        else if (i + 1 == new Date().toLocaleDateString('ru-RU').substring(0, 2) && month_curr_date == new Date().getMonth() + 1 &&
            year_curr_date == new Date().getFullYear())
            table_day += "<td align='center' onclick='getDayClickButton(" + (i + 1) + "," + month_curr_date + "," + year_curr_date + "," + id_element + ")' style='background: #9fdee2; color: white; cursor: default'>" + (i + 1);
        else table_day += "<td align='center'  onclick='getDayClickButton(" + (i + 1) + "," + month_curr_date + "," + year_curr_date + "," + id_element + ")' style='cursor: default' class='risk-table-light'>" + (i + 1);
        table_day_week++;
        if (table_day_week == 7) table_day_week = 0;
    }
    for (var i = 0; i < 7 - D1Nlast; i++) {
        if (D1Nlast == 0) break;
        table_day += "<td>";
    }
    all_date.all_date_table = table_editer + table_start + table_header + table_day + table_end;
    return all_date;
}
function getDayClickButton(day, month, year, id_element) {
    var format = '';
    if (typeof id_element != "undefined") {
        var date = document.getElementById(id_element.id).value;
        format = getLocalFormat(date);
    }
    var _day = day;
    if (_day < 10) _day = "0" + _day;
    var _month = month;
    if (_month < 10) _month = "0" + _month;
    var new_date = _day + "." + _month + "." + year;
    if (format == 'js') new_date = dateFormatJavaScript(new_date);
    if (typeof id_element != "undefined") {
        document.getElementById(id_element.id).value = new_date;
        document.getElementById(id_element.id + "Edit").style.display = "none";
    }

}

function getDateEditDotSeparate(id_element, type) {
    var _date = document.getElementById(id_element).value;
    var format = '';
    format = getLocalFormat(_date);
    var year = _date.substring(6, 10);
    var month = _date.substring(3, 5);
    var day = _date.substring(0, 2);
    var zero = '';
    if (format == 'js') {
        year = _date.substring(0, 4);
        month = _date.substring(5, 7);
        day = _date.substring(8, 10);
    }
    if (type == 'monthPlus') {
        if (month == 12) {
            month = 0;
            year = (year * 1 + 1)
        }
        if (month < 9) zero = '0';
        _date = day + '.' + zero + (month * 1 + 1) + '.' + year;
    }
    if (type == 'monthMinus') {
        if (month == 1) {
            month = 13;
            year = (year * 1 - 1)
        }
        if (month < 11) zero = '0';
        _date = day + '.' + zero + (month * 1 - 1) + '.' + year;
    }
    if (type == 'yearPlus')
        _date = day + '.' + month + '.' + (year * 1 + 1);
    if (type == 'yearMinus')
        _date = day + '.' + month + '.' + (year * 1 - 1);
    if (format == 'js') {
        _date = dateFormatJavaScript(_date);
    }
    if (type == 'dateDel') {
        _date = '';
        document.getElementById(id_element + "Edit").style.display = "none";
    }
    document.getElementById(id_element).value = _date;
    if (type != 'dateDel')
        document.getElementById(id_element+"Table").innerHTML = getCalendarOnlyJS(id_element, _date)
}

function dateFormatJavaScript(item) {
    if (item != null) {
        var format = getLocalFormat(item);
        if (format == 'js')
            item = item.substring(8, 10) + "." + item.substring(5, 7) + "." + item.substring(0, 4);
        else
            item = item.substring(6, 10) + "-" + item.substring(3, 5) + "-" + item.substring(0, 2);
        return item
    } else {
        return null
    }
}
function getLocalFormat(_date) {

    var format = '';
    for (var i = 0; i < _date.length; i++) {
        if (_date[i] == '-') {
            format = 'js';
            break;
        }
    }
    return format
}