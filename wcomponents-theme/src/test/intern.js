// Properties will be expanded in this file before it is used.
// Test suites will be injected into this file via the "test.suites" property.
//
// Learn more about configuring this file at <https://github.com/theintern/intern/wiki/Configuring-Intern>.
define({
	suites: [ ${unit.tests} ],

	// The port on which the instrumenting proxy will listen
	proxyPort: 9000,

	// A fully qualified URL to the Intern proxy
	proxyUrl: 'http://localhost:9000/',

	tunnel: 'SauceLabsTunnel',
	tunnelOptions: {
		verbose: 'true'
	},
	environments: [ ${test.environments} ],

	// Configuration options for the module loader; any AMD configuration options supported by the specified AMD loader
	// can be used here
	loaderOptions: {
		// Packages that should be registered with the loader in each testing environment
		paths: {
			wc: '${amd.src.dir}/wc',
			dojo: '${amd.src.dir}/dojo',
			sprintf: '${amd.src.dir}/sprintf',
			Promise: '${amd.src.dir}/promise/Promise.min',
			compat: '${amd.src.dir}/wc/compat'
		}
	},
	// A regular expression matching URLs to files that should not be included in code coverage analysis
	excludeInstrumentation: /^(?:.*\${file.separator}test\-classes|.*\${file.separator}node_modules|.*\${file.separator}dojo|.*\${file.separator}sprintf)\${file.separator}/
});
