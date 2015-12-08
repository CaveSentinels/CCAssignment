#!/usr/bin/env node

var lineReader = require('line-reader');

// File statistical data.
var word_count = 0;
var line_count = 0;
var word_occurrence = {};
var edit_distances = [];
var word_list = [];
var trigrams = {};
var word_sorted_list = [];
var sorted_trigrams = [];
var trigrams_edit_distances = {};
var sorted_edit_distances = [];

// Get the file path.
var file_path = process.argv[2];

function dbg_print(obj) {
    console.log(obj);
}

function split_line(line) {
    return line.split(/[0-9|\s|\_|\+|\-|\t|\n|\.|;|!|?|,|~|@|#|$|%|^|&|*|(|)|\\|\/|\||<|>|"|=|:]+/g);
}

function merge_word_list(words) {
    var copy = [];
    var word = null;
    for (var i = 0; i < words.length; ++i) {
        word = words[i];
        if (word != null && word.length > 0) {
            copy.push(word);
        }
    }
    word_list = word_list.concat(copy);
}

function count_word_occurrence(words) {
    var word = "";
    var i = 0;
    for (i = 0; i < words.length; ++i) {
        word = words[i];
        if (word != null && word.length > 0) {
            if (word_occurrence[word] != null) {
                word_occurrence[word] += 1;
            } else {
                word_occurrence[word] = 1;
            }
        }
    }
}

function sort_words() {
    var items = Object.keys(word_occurrence).map(function(key) {
        return [key, word_occurrence[key]];
    });

    items.sort(function(first, second) {
        return second[1] - first[1];
    });

    word_sorted_list = items;
}

function generate_trigrams() {
    var trigram = null;
    var i = 0;
    for (i = 0; i < word_list.length - 3; ++i) {
        trigram = word_list[i] + ' ' + word_list[i+1] + ' ' + word_list[i+2];
        if (trigrams[trigram] != null) {
            trigrams[trigram] += 1;
        } else {
            trigrams[trigram] = 1;
        }
    }
}

function sort_trigrams() {
    var items = Object.keys(trigrams).map(function(key) {
        return [key, trigrams[key]];
    });

    items.sort(function(first, second) {
        return second[1] - first[1];
    });

    sorted_trigrams = items.slice(0, 11);
}

function calc_edit_distance(str1, str2) {
    var len1 = str1.length;
    var len2 = str2.length;

    // Handle special cases.
    if (len1 == 0) {
        return len2;
    }

    if (len2 == 0) {
        return len1;
    }

    // Create the 2D array.
    var dist = [];
    for (var i = 0; i <= len1; ++i) {
        var row = [];
        for (var j = 0; j <= len2; ++j) {
            row.push(0);
        }
        dist.push(row);
    }

    // Fill in the edge conditions.
    for (var i = 0; i <= len1; ++i) {
        dist[i][0] = i;
    }
    for (var j = 0; j <= len2; ++j) {
        dist[0][j] = j;
    }

    // Calculate the edit distance.
    for (var i = 1; i <= len1; ++i) {
        for (var j = 1; j <= len2; ++j) {
            dist[i][j] = Math.min(dist[i-1][j-1] + (str1[i] == str2[j] ? 0 : 1),
                dist[i-1][j] + 1, dist[i][j-1] + 1
            );
        }
    }

    return dist[len1][len2];
}

function calc_edit_distances() {
    var base_line = sorted_trigrams[0][0];
    for (var i = 1; i < sorted_trigrams.length; ++i) {
        var comp = sorted_trigrams[i][0];
        trigrams_edit_distances[comp] = calc_edit_distance(base_line, comp);
    }
}

function sort_edit_distances() {
    var items = Object.keys(trigrams_edit_distances).map(function(key) {
        return [key, trigrams_edit_distances[key]];
    });

    items.sort(function(first, second) {
        return second[1] - first[1];
    });

    sorted_edit_distances = items.slice(0, 11);
}

function html(str) {
    console.log(str);
}

function generate_html_report() {
    html("<!DOCTYPE html>");
    html("<html>");

    html("<head>");
        html("<title>Text Processing Report</title>");
    html("</head>");

    html("<body>");
        html("<h1>Text Processing Report</h1>");

        html("<h2>Source File</h2>");
        html(file_path);

        html("<h2>Section 1: General Statistics</h2>");
        html("<table border=\"1\" cellpadding=\"3\" cellspacing=\"3\">");
        	html("<tr>");
        		html("<td>Word Count</td>");
        		html("<td>Line Count</td>");
        	html("</tr>");
        	html("<tr>");
        		html("<td>" + word_count + "</td>");
        		html("<td>" + line_count + "</td>");
        	html("</tr>");
        html("</table>");

        html("<h2>Section 2: Word List</h2>");
        html("<table border=\"1\" cellpadding=\"3\" cellspacing=\"3\">");
            html("<tr>");
                html("<th>Word</th>");
                html("<th>Occurrences</th>");
            html("</tr>");
        for (var i = 0; i < word_sorted_list.length; ++i) {
            var w = word_sorted_list[i][0];
            var c = word_sorted_list[i][1];
            html("<tr>");
                html("<td>" + w + "</td>");
                html("<td>" + c + "</td>");
            html("</tr>");
        }
        html("</table>");

        html("<h2>Section 3: Top 10 Most Common Trigrams</h2>");
        html("<table border=\"1\" cellpadding=\"3\" cellspacing=\"3\">");
            html("<tr>");
                html("<th>Trigram</th>");
                html("<th>Occurrences</th>");
            html("</tr>");
        for (var i = 0; i < sorted_trigrams.length; ++i) {
            var t = sorted_trigrams[i][0];
            var c = sorted_trigrams[i][1];
            html("<tr>");
                html("<td>" + t + "</td>");
                html("<td>" + c + "</td>");
            html("</tr>");
        }
        html("</table>");

        html("<h2>Section 4: Top 10 Trigrams Edit Distances</h2>");
        html("<table border=\"1\" cellpadding=\"3\" cellspacing=\"3\">");
            html("<tr>");
                html("<th>Trigram#1</th>");
                html("<th>Trigram#2</th>");
                html("<th>Edit Distance</th>");
            html("</tr>");
        for (var i = 0; i < sorted_edit_distances.length; ++i) {
            var s = sorted_trigrams[0][0];
            var t = sorted_edit_distances[i][0];
            var c = sorted_edit_distances[i][1];
            html("<tr>");
                html("<td>" + s + "</td>");
                html("<td>" + t + "</td>");
                html("<td>" + c + "</td>");
            html("</tr>");
        }
        html("</table>");

    html("</body>");

    html("</html>");
}

// Read line by line.
lineReader.eachLine(file_path, function(line, last) {
    // Count the line.
    if (line != null && line.length > 0) {
        ++line_count;
    }

    // Merge the words in the current line to the word list.
    var words = split_line(line);
    word_count += words.length;
    merge_word_list(words);
    count_word_occurrence(words);

    if (last) {
        sort_words();
        generate_trigrams();
        sort_trigrams();
        calc_edit_distances();
        sort_edit_distances();
        generate_html_report();
    }
})
