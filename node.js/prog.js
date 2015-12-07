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

// var separators = ['\\\s'];
// split_regexp = new RegExp(separators.join('|'), 'g');

// Get the file path.
var file_path = process.argv[2];

function dbg_print(obj) {
    console.log(obj);
}

function count_line(line) {
    if (line != null && line.length > 0) {
        ++line_count;
    }
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

function count_words() {
    word_count = Object.keys(word_occurrence).length;
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

    sorted_trigrams = items;
}

function generate_html_report() {
    // TODO: Implement me!
}

// Read line by line.
lineReader.eachLine(file_path, function(line, last) {
    // dbg_print(line);

    // Count the line.
    count_line(line);

    // Merge the words in the current line to the word list.
    var words = split_line(line);
    merge_word_list(words);
    // count_word_occurrence(words);
    // count_words();
    // sort_words();

    if (last) {
        generate_trigrams();
        sort_trigrams();
        dbg_print(sorted_trigrams);
        // dbg_print(word_occurrence);
        // dbg_print(word_list);
        // dbg_print(word_sorted_list);
        // dbg_print(line_count);
        // dbg_print(word_count);
    }
})
