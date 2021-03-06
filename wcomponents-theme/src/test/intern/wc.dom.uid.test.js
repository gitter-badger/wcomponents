define(["intern!object", "intern/chai!assert", "./resources/test.utils"],
	function (registerSuite, assert, testutils) {
		"use strict";

		var uid, unique, ids, len = 100;

		registerSuite({
			name: "domUid",
			setup: function() {
				return testutils.setupHelper(["wc/dom/uid", "wc/array/unique"], function(obj, u) {
					uid = obj;
					unique = u;

					var i = 0;
					ids = [];
					while (i++ < len) {
						ids[ids.length] = uid();
					}
				});
			},
			testPrecondition: function() {
				assert.strictEqual(len, ids.length, "Test case not built correctly");
			},
			testUniqueness: function() {
				ids = unique(ids);
				assert.strictEqual(len, ids.length, "Duplicate ids were generated by uid");
			},
			testSortable: function() {
				var i;
				for (i = 1; i < ids.len; i++) {
					assert.isTrue(ids[i - 1] < ids[i], "ids are not sortable");
				}
			}
		});
	});
