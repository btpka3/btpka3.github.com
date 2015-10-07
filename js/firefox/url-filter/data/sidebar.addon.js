"use strict";
var panel = require("sdk/panel");
var ui = require('sdk/ui');
var tabs = require("sdk/tabs");
var self = require('sdk/self');

var sidebar = ui.Sidebar({
    id: 'my-sidebar',
    title: 'My sidebar',
    url: './sidebar.html'
});
sidebar.show();