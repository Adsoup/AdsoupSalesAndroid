webpackJsonp([9],{"./app/common-components/NotFoundPage/404.png":function(e,n,t){e.exports=t.p+"e44b9dfc64490656c207f503ff82aa85.png"},"./app/common-components/NotFoundPage/index.js":function(e,n,t){"use strict";function o(e,n){if(!(e instanceof n))throw new TypeError("Cannot call a class as a function")}function i(e,n){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!n||"object"!=typeof n&&"function"!=typeof n?e:n}function r(e,n){if("function"!=typeof n&&null!==n)throw new TypeError("Super expression must either be null or a function, not "+typeof n);e.prototype=Object.create(n&&n.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),n&&(Object.setPrototypeOf?Object.setPrototypeOf(e,n):e.__proto__=n)}Object.defineProperty(n,"__esModule",{value:!0});var a=t("./node_modules/react/react.js"),l=t.n(a),p=(t("./node_modules/prop-types/index.js"),t("./node_modules/react-intl/lib/index.es.js")),s=Object(p.d)({header:{id:"app.components.NotFoundPage.header",defaultMessage:"Not here."},subtitle:{id:"app.components.NotFoundPage.subtitle",defaultMessage:"sorry but the page cannot be found"},link:{id:"app.components.NotFoundPage.link",defaultMessage:"let's go back"}}),c=t("./node_modules/styled-components/dist/styled-components.es.js"),u=t("./app/common-components/StyleUtils/index.js"),f=function(e,n){return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(n)}}))}(["\n    .header {\n      font-size: 40px;\n      margin-bottom: -10px;\n    }\n\n    .subtitle, .link {\n      font-size: 20px;\n      margin-bottom: 15px;\n    }\n\n    img {\n      width: initial;\n      max-height: 50vh;\n      max-width: 80vw;\n    }\n  "],["\n    .header {\n      font-size: 40px;\n      margin-bottom: -10px;\n    }\n\n    .subtitle, .link {\n      font-size: 20px;\n      margin-bottom: 15px;\n    }\n\n    img {\n      width: initial;\n      max-height: 50vh;\n      max-width: 80vw;\n    }\n  "]),d=c.b.div.withConfig({displayName:"Wrapper__Wrapper"})(["min-width: 100%;width: 100vw;display: flex;flex-direction: column;justify-content: center;position: fixed;top: 0;left: 0;z-index: 100;align-items: center;height: 100%;background-color: ",';img {width: 50vw;max-width: 700px;}.header {font-size: 56px;margin-bottom: -20px;}.subtitle, .link {font-size: 30px;margin-bottom: 10px;}.header, .subtitle, .link {color: white;font-weight: lighter;}.link:hover {cursor: pointer;}.link::after {box-shadow: inset 0 0px 0 white, inset 0 -0.5px 0 white;content: " ";position: relative;display: inline-block;height: 10px;width: 100%;float: left;margin-top: -18px;}',""],u.x,u.z.mobile(f)),m=d,h=t("./app/common-components/NotFoundPage/404.png"),b=t.n(h),g=function(){var e="function"==typeof Symbol&&Symbol.for&&Symbol.for("react.element")||60103;return function(n,t,o,i){var r=n&&n.defaultProps,a=arguments.length-3;if(t||0===a||(t={}),t&&r)for(var l in r)void 0===t[l]&&(t[l]=r[l]);else t||(t=r||{});if(1===a)t.children=i;else if(a>1){for(var p=Array(a),s=0;s<a;s++)p[s]=arguments[s+3];t.children=p}return{$$typeof:e,type:n,key:void 0===o?null:""+o,ref:null,props:t,_owner:null}}}(),x=function(){function e(e,n){for(var t=0;t<n.length;t++){var o=n[t];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}return function(n,t,o){return t&&e(n.prototype,t),o&&e(n,o),n}}(),y=g("img",{src:b.a,alt:"404"}),w=function(e){function n(){return o(this,n),i(this,(n.__proto__||Object.getPrototypeOf(n)).apply(this,arguments))}return r(n,e),x(n,[{key:"render",value:function(){return g(m,{},void 0,y,g("h1",{className:"header"},void 0,l.a.createElement(p.a,s.header)),g("h3",{className:"subtitle"},void 0,l.a.createElement(p.a,s.subtitle)),g("button",{role:"link",className:"link",tabIndex:"0",onClick:this.props.history.goBack},void 0,l.a.createElement(p.a,s.link)))}}]),n}(l.a.PureComponent);n.default=w}});