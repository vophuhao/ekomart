/*=== Javascript function indexing hear===========

1.counterUp ----------(Its use for counting number)
2.stickyHeader -------(header class sticky)
3.wowActive ----------( Waw js plugins activation)
4.swiperJs -----------(All swiper in this website hear)
5.salActive ----------(Sal animation for card and all text)
6.textChanger --------(Text flip for banner section)
7.timeLine -----------(History Time line)
8.datePicker ---------(On click date calender)
9.timePicker ---------(On click time picker)
10.timeLineStory -----(History page time line)
11.vedioActivation----(Vedio activation)
12.searchOption ------(search open)
13.cartBarshow -------(Cart sode bar)
14.sideMenu ----------(Open side menu for desktop)
15.Back to top -------(back to top)
16.filterPrice -------(Price filtering)

==================================================*/

(function($) {
	'use strict';
	var rtsJs = {
		m: function(e) {
			rtsJs.d();
			rtsJs.methods();
		},
		d: function(e) {
			this._window = $(window),
				this._document = $(document),
				this._body = $('body'),
				this._html = $('html')
		},
		methods: function(e) {
			rtsJs.preloader();
			rtsJs.stickyHeader();
			rtsJs.backToTopInit();
			rtsJs.swiperActivation();
			rtsJs.cartNumberIncDec();
			rtsJs.countDown();
			// rtsJs.zoonImage();
			rtsJs.modalpopupCard();
			rtsJs.filter();
			rtsJs.counterUp();
			rtsJs.niceSelect();
			rtsJs.stickySidebar();
			rtsJs.sideMenu();
			rtsJs.searchOption();
			rtsJs.menuCurrentLink();
			rtsJs.modalOver();
			rtsJs.darklightSwitcher();
		},

		preloader: function(e) {
			$(window).on('load', function() {
				$("#rts__preloader").delay(0).fadeOut(1000);
			})
			$(window).on('load', function() {
				$("#weiboo-load").delay(0).fadeOut(1000);
			})
		},

		// sticky Header Activation
		stickyHeader: function(e) {
			$(window).scroll(function() {
				if ($(this).scrollTop() > 150) {
					$('.header--sticky').addClass('sticky')
				} else {
					$('.header--sticky').removeClass('sticky')
				}
			})
		},

		// backto Top Area Start
		backToTopInit: function() {
			$(document).ready(function() {
				"use strict";

				var progressPath = document.querySelector('.progress-wrap path');
				var pathLength = progressPath.getTotalLength();
				progressPath.style.transition = progressPath.style.WebkitTransition = 'none';
				progressPath.style.strokeDasharray = pathLength + ' ' + pathLength;
				progressPath.style.strokeDashoffset = pathLength;
				progressPath.getBoundingClientRect();
				progressPath.style.transition = progressPath.style.WebkitTransition = 'stroke-dashoffset 10ms linear';
				var updateProgress = function() {
					var scroll = $(window).scrollTop();
					var height = $(document).height() - $(window).height();
					var progress = pathLength - (scroll * pathLength / height);
					progressPath.style.strokeDashoffset = progress;
				}
				updateProgress();
				$(window).scroll(updateProgress);
				var offset = 150;
				var duration = 500;
				jQuery(window).on('scroll', function() {
					if (jQuery(this).scrollTop() > offset) {
						jQuery('.progress-wrap').addClass('active-progress');
					} else {
						jQuery('.progress-wrap').removeClass('active-progress');
					}
				});
				jQuery('.progress-wrap').on('click', function(event) {
					event.preventDefault();
					jQuery('html, body').animate({ scrollTop: 0 }, duration);
					return false;
				})


			});

		},


		swiperActivation: function() {
			$(document).ready(function() {
				let defaults = {
					spaceBetween: 30,
					slidesPerView: 2
				};
				// call init function
				initSwipers(defaults);

				function initSwipers(defaults = {}, selector = ".swiper-data") {
					let swipers = document.querySelectorAll(selector);
					swipers.forEach((swiper) => {
						// get options
						let optionsData = swiper.dataset.swiper
							? JSON.parse(swiper.dataset.swiper)
							: {};
						// combine defaults and custom options
						let options = {
							...defaults,
							...optionsData
						};

						//console.log(options);
						// init
						new Swiper(swiper, options);
					});
				}

			})

			$(document).ready(function() {

				var sliderThumbnail = new Swiper(".slider-thumbnail", {
					spaceBetween: 20,
					slidesPerView: 3,
					freeMode: true,
					watchSlidesVisibility: true,
					watchSlidesProgress: true,
					breakpoints: {
						991: {
							spaceBetween: 30,
						},
						320: {
							spaceBetween: 15,
						}
					},
				});

				var swiper = new Swiper(".swiper-container-h12", {
					thumbs: {
						swiper: sliderThumbnail,
					},
				});

			});

		},


		cartNumberIncDec: function() {
			$(document).ready(function() {

				$(function() {
					$(".button").on("click", function() {
						var $button = $(this);
						var $parent = $button.parents('.quantity-edit');
						var oldValue = $parent.find('.input').val();

						if ($button.text() == "+") {
							var newVal = parseFloat(oldValue) + 1;
						} else {
							// Don't allow decrementing below zero
							if (oldValue > 1) {
								var newVal = parseFloat(oldValue) - 1;
							} else {
								newVal = 1;
							}
						}
						$parent.find('a.add-to-cart').attr('data-quantity', newVal);
						$parent.find('.input').val(newVal);
					});
				});
			});

			$(".coupon-click").on('click', function() {
				$(this).parents('.coupon-input-area-1').find(".coupon-input-area").toggleClass('show');
			});

			$('.close-c1').on('click', function() {
				$('.close-c1'), $(this).parents('.cart-item-1').addClass('deactive');
			});

		},

		countDown: function() {
			$(function() {
				countDown.init();
				updateCountdowns();
				setInterval(updateCountdowns, 1000);

				function updateCountdowns() {
					countDown.validElements.forEach((element, i) => {
						countDown.changeTime(element, countDown.endDate[i], i);
					});
				}
			});

			const countDown = {
				endDate: [],
				validElements: [],
				display: [],
				initialHeight: undefined,
				initialInnerDivMarginTop: undefined,
				originalBorderTopStyle: undefined,

				init: function() {
					$('.countDown').each(function() {
						const regex_match = $(this).text().match(/([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4}) ([0-9]{2}):([0-9]{2}):([0-9]{2})/);
						if (!regex_match) return;

						const end = new Date(regex_match[3], regex_match[2] - 1, regex_match[1], regex_match[4], regex_match[5], regex_match[6]);

						if (end > new Date()) {
							countDown.validElements.push($(this));
							countDown.endDate.push(end);
							countDown.changeTime($(this), end, countDown.validElements.length - 1);
							$(this).html(countDown.display.next.map(item => `<div class='container'><div class='a'><div>${item}</div></div></div>`).join(''));
						} else {
							// Display your message when the countdown expires
							$(this).html("<p class='end'>Sorry, your session has expired.</p>");
						}
					});
				},

				reset: function(element) {
					// This function appears to be incomplete, as it currently doesn't do anything.
				},

				changeTime: function(element, endTime) {
					if (!endTime) return;

					const today = new Date();
					if (today.getTime() <= endTime.getTime()) {
						countDown.display = {
							'last': this.calcTime(endTime.getTime() - today.getTime() + 1000),
							'next': this.calcTime(endTime.getTime() - today.getTime())
						};
						countDown.display.next = countDown.display.next.map(item => item.toString().padStart(2, '0'));
						countDown.display.last = countDown.display.last.map(item => item.toString().padStart(2, '0'));

						element.find('div.container div.a div').each((index, div) => {
							$(div).text(countDown.display.last[index]);
						});

						this.reset(element.find('div.container'));
					} else {
						element.html("<p class='end'>Sorry, your session has expired.</p>");
					}
				},

				calcTime: function(milliseconds) {
					const secondsTotal = Math.floor(milliseconds / 1000);
					const days = Math.floor(secondsTotal / 86400);
					const hours = Math.floor((secondsTotal % 86400) / 3600);
					const minutes = Math.floor((secondsTotal % 3600) / 60);
					const seconds = secondsTotal % 60;
					return [days, hours, minutes, seconds];
				}
			};

		},


		zoonImage: function() {
			$(document).ready(function() {
				function imageZoom(imgID, resultID) {
					var img, lens, result, cx, cy;
					img = document.getElementById(imgID);
					result = document.getElementById(resultID);
					/*create lens:*/
					lens = document.createElement("DIV");
					lens.setAttribute("class", "img-zoom-lens");
					/*insert lens:*/
					img.parentElement.insertBefore(lens, img);
					/*calculate the ratio between result DIV and lens:*/
					cx = result.offsetWidth / lens.offsetWidth;
					cy = result.offsetHeight / lens.offsetHeight;
					/*set background properties for the result DIV:*/
					result.style.backgroundImage = "url('" + img.src + "')";
					result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
					/*execute a function when someone moves the cursor over the image, or the lens:*/
					lens.addEventListener("mousemove", moveLens);
					img.addEventListener("mousemove", moveLens);
					/*and also for touch screens:*/
					lens.addEventListener("touchmove", moveLens);
					img.addEventListener("touchmove", moveLens);
					function moveLens(e) {
						var pos, x, y;
						/*prevent any other actions that may occur when moving over the image:*/
						e.preventDefault();
						/*get the cursor's x and y positions:*/
						pos = getCursorPos(e);
						/*calculate the position of the lens:*/
						x = pos.x - (lens.offsetWidth / 2);
						y = pos.y - (lens.offsetHeight / 2);
						/*prevent the lens from being positioned outside the image:*/
						if (x > img.width - lens.offsetWidth) { x = img.width - lens.offsetWidth; }
						if (x < 0) { x = 0; }
						if (y > img.height - lens.offsetHeight) { y = img.height - lens.offsetHeight; }
						if (y < 0) { y = 0; }
						/*set the position of the lens:*/
						lens.style.left = x + "px";
						lens.style.top = y + "px";
						/*display what the lens "sees":*/
						result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
					}
					function getCursorPos(e) {
						var a, x = 0, y = 0;
						e = e || window.event;
						/*get the x and y positions of the image:*/
						a = img.getBoundingClientRect();
						/*calculate the cursor's x and y coordinates, relative to the image:*/
						x = e.pageX - a.left;
						y = e.pageY - a.top;
						/*consider any page scrolling:*/
						x = x - window.pageXOffset;
						y = y - window.pageYOffset;
						return { x: x, y: y };
					}
				}

				imageZoom("myimage", "myresult");


			});
		},


		modalpopupCard: function() {
			// Newsletter popup
			$(document).ready(function() {
				function showpanel() {
					$(".anywere-home").addClass("bgshow");
					$(".rts-newsletter-popup").addClass("popup");
				}
				setTimeout(showpanel, 4000)
			});

			$(".anywere-home").on('click', function() {
				$(".rts-newsletter-popup").removeClass("popup")
				$(".anywere-home").removeClass("bgshow")
			});
			$(".newsletter-close-btn").on('click', function() {
				$(".rts-newsletter-popup").removeClass("popup")
				$(".anywere-home").removeClass("bgshow")
			});

			// Product details popup
			$(".product-details-popup-btn").on('click', function() {
				$(".product-details-popup-wrapper").addClass("popup")
				$("#anywhere-home").addClass("bgshow");
			});
			$(".product-bottom-action .view-btn").on('click', function() {
				$(".product-details-popup-wrapper").addClass("popup");
				$("#anywhere-home").addClass("bgshow");
			});
			$(".product-details-popup-wrapper .cart-edit").on('click', function() {
				$(".product-details-popup-wrapper").addClass("popup");
				$("#anywhere-home").addClass("bgshow");
			});

			$(".product-details-close-btn").on('click', function() {
				$(".product-details-popup-wrapper").removeClass("popup");
				$("#anywhere-home").removeClass("bgshow");
			});

			$(".message-show-action").on('click', function() {
				$(".successfully-addedin-wishlist").show(500);
				$("#anywhere-home").addClass("bgshow");
			});

			$("#anywhere-home").on('click', function() {
				$(".successfully-addedin-wishlist").hide(0);
				$("#anywhere-home").removeClass("bgshow");
			});

			$("#anywhere-home").on('click', function() {
				$(".product-details-popup-wrapper").removeClass("popup");
				$("#anywhere-home").removeClass("bgshow");
			});



			// anywhere home

			$(document).ready(function() {
				function showpanel() {
					$(".anywere-home").addClass("bgshow");
					$(".rts-newsletter-popup").addClass("popup");
				}
				setTimeout(showpanel, 4000)
			});

			$(".anywere-home").on('click', function() {
				$(".rts-newsletter-popup").removeClass("popup");
				$(".anywere-home").removeClass("bgshow");
			});
			$(".newsletter-close-btn").on('click', function() {
				$(".rts-newsletter-popup").removeClass("popup")
				$(".anywere-home").removeClass("bgshow")
			});

			// Product details popup
			$(".product-details-popup-btn").on('click', function() {
				$(".product-details-popup-wrapper").addClass("popup")
				$(".anywere").addClass("bgshow");
			});
			$(".product-bottom-action .view-btn").on('click', function() {
				$(".product-details-popup-wrapper").addClass("popup");
				$(".anywere").addClass("bgshow");
			});
			$(".product-details-popup-wrapper .cart-edit").on('click', function() {
				$(".product-details-popup-wrapper").addClass("popup");
				$(".anywere-home").addClass("bgshow");
			});

			$(".product-details-close-btn").on('click', function() {
				$(".product-details-popup-wrapper").removeClass("popup");
				$(".anywere").removeClass("bgshow");
			});
			$(".anywere").on('click', function() {
				$(".product-details-popup-wrapper").removeClass("popup");
				$(".anywere").removeClass("bgshow");
			});


			$('.section-activation').on('click', function() {
				$('.section-activation'), $(this).parents('.item-parent').addClass('deactive');
			});

		},

		filter: function() {
			// Filter item
			$(document).on('click', '.filter-btn', function() {
				var show = $(this).data('show');
				$(show).removeClass("hide").siblings().addClass("hide");
			});

			$(document).on('click', '.filter-btn', function() {
				$(this).addClass('active').siblings().removeClass('active');
			})

		},

		counterUp: function() {
			$('.counter').counterUp({
				delay: 10,
				time: 2000
			});
			$('.counter').addClass('animated fadeInDownBig');
			$('h3').addClass('animated fadeIn');

		},

		niceSelect: function() {
			(function($) {

				$.fn.niceSelect = function(method) {

					// Methods
					if (typeof method == 'string') {
						if (method == 'update') {
							this.each(function() {
								var $select = $(this);
								var $dropdown = $(this).next('.nice-select');
								var open = $dropdown.hasClass('open');

								if ($dropdown.length) {
									$dropdown.remove();
									create_nice_select($select);

									if (open) {
										$select.next().trigger('click');
									}
								}
							});
						} else if (method == 'destroy') {
							this.each(function() {
								var $select = $(this);
								var $dropdown = $(this).next('.nice-select');

								if ($dropdown.length) {
									$dropdown.remove();
									$select.css('display', '');
								}
							});
							if ($('.nice-select').length == 0) {
								$(document).off('.nice_select');
							}
						} else {
							console.log('Method "' + method + '" does not exist.')
						}
						return this;
					}

					// Hide native select
					this.hide();

					// Create custom markup
					this.each(function() {
						var $select = $(this);

						if (!$select.next().hasClass('nice-select')) {
							create_nice_select($select);
						}
					});

					function create_nice_select($select) {
						$select.after($('<div></div>')
							.addClass('nice-select')
							.addClass($select.attr('class') || '')
							.addClass($select.attr('disabled') ? 'disabled' : '')
							.attr('tabindex', $select.attr('disabled') ? null : '0')
							.html('<span class="current"></span><ul class="list"></ul>')
						);

						var $dropdown = $select.next();
						var $options = $select.find('option');
						var $selected = $select.find('option:selected');

						$dropdown.find('.current').html($selected.data('display') || $selected.text());

						$options.each(function(i) {
							var $option = $(this);
							var display = $option.data('display');

							$dropdown.find('ul').append($('<li></li>')
								.attr('data-value', $option.val())
								.attr('data-display', (display || null))
								.addClass('option' +
									($option.is(':selected') ? ' selected' : '') +
									($option.is(':disabled') ? ' disabled' : ''))
								.html($option.text())
							);
						});
					}

					/* Event listeners */

					// Unbind existing events in case that the plugin has been initialized before
					$(document).off('.nice_select');

					// Open/close
					$(document).on('click.nice_select', '.nice-select', function(event) {
						var $dropdown = $(this);

						$('.nice-select').not($dropdown).removeClass('open');
						$dropdown.toggleClass('open');

						if ($dropdown.hasClass('open')) {
							$dropdown.find('.option');
							$dropdown.find('.focus').removeClass('focus');
							$dropdown.find('.selected').addClass('focus');
						} else {
							$dropdown.focus();
						}
					});

					// Close when clicking outside
					$(document).on('click.nice_select', function(event) {
						if ($(event.target).closest('.nice-select').length === 0) {
							$('.nice-select').removeClass('open').find('.option');
						}
					});

					// Option click
					$(document).on('click.nice_select', '.nice-select .option:not(.disabled)', function(event) {
						var $option = $(this);
						var $dropdown = $option.closest('.nice-select');

						$dropdown.find('.selected').removeClass('selected');
						$option.addClass('selected');

						var text = $option.data('display') || $option.text();
						$dropdown.find('.current').text(text);

						$dropdown.prev('select').val($option.data('value')).trigger('change');
					});

					// Keyboard events
					$(document).on('keydown.nice_select', '.nice-select', function(event) {
						var $dropdown = $(this);
						var $focused_option = $($dropdown.find('.focus') || $dropdown.find('.list .option.selected'));

						// Space or Enter
						if (event.keyCode == 32 || event.keyCode == 13) {
							if ($dropdown.hasClass('open')) {
								$focused_option.trigger('click');
							} else {
								$dropdown.trigger('click');
							}
							return false;
							// Down
						} else if (event.keyCode == 40) {
							if (!$dropdown.hasClass('open')) {
								$dropdown.trigger('click');
							} else {
								var $next = $focused_option.nextAll('.option:not(.disabled)').first();
								if ($next.length > 0) {
									$dropdown.find('.focus').removeClass('focus');
									$next.addClass('focus');
								}
							}
							return false;
							// Up
						} else if (event.keyCode == 38) {
							if (!$dropdown.hasClass('open')) {
								$dropdown.trigger('click');
							} else {
								var $prev = $focused_option.prevAll('.option:not(.disabled)').first();
								if ($prev.length > 0) {
									$dropdown.find('.focus').removeClass('focus');
									$prev.addClass('focus');
								}
							}
							return false;
							// Esc
						} else if (event.keyCode == 27) {
							if ($dropdown.hasClass('open')) {
								$dropdown.trigger('click');
							}
							// Tab
						} else if (event.keyCode == 9) {
							if ($dropdown.hasClass('open')) {
								return false;
							}
						}
					});

					// Detect CSS pointer-events support, for IE <= 10. From Modernizr.
					var style = document.createElement('a').style;
					style.cssText = 'pointer-events:auto';
					if (style.pointerEvents !== 'auto') {
						$('html').addClass('no-csspointerevents');
					}

					return this;

				};

			}(jQuery));

			/* Initialize */

			$(document).ready(function() {
				$('select').niceSelect();
			});
		},

		stickySidebar: function() {
			if (typeof $.fn.theiaStickySidebar !== "undefined") {
				$(".rts-sticky-column-item").theiaStickySidebar({
					additionalMarginTop: 130,
				});
			}
		},

		sideMenu: function() {
			$(document).on('click', '.menu-btn', function() {
				$("#side-bar").addClass("show");
				$("#anywhere-home").addClass("bgshow");
			});
			$(document).on('click', '.close-icon-menu', function() {
				$("#side-bar").removeClass("show");
				$("#anywhere-home").removeClass("bgshow");
			});
			$(document).on('click', '#anywhere-home', function() {
				$("#side-bar").removeClass("show");
				$("#anywhere-home").removeClass("bgshow");
			});
			$(document).on('click', '.onepage .mainmenu li a', function() {
				$("#side-bar").removeClass("show");
				$("#anywhere-home").removeClass("bgshow");
			});
			$('#mobile-menu-active').metisMenu();
			$('#category-active-four').metisMenu();
			$('#category-active-menu').metisMenu();
			$('.category-active-menu-sidebar').metisMenu();
		},

		// search popup
		searchOption: function() {
			$(document).on('click', '#search', function() {
				$(".search-input-area").addClass("show");
				$("#anywhere-home").addClass("bgshow");
			});
			$(document).on('click', '#close', function() {
				$(".search-input-area").removeClass("show");
				$("#anywhere-home").removeClass("bgshow");
			});
			$(document).on('click', '#anywhere-home', function() {
				$(".search-input-area").removeClass("show");
				$("#anywhere-home").removeClass("bgshow");
			});
		},

		menuCurrentLink: function() {
			var currentPage = location.pathname.split("https://html.themewant.com/"),
				current = currentPage[currentPage.length - 1];
			$('.parent-nav li a').each(function() {
				var $this = $(this);
				if ($this.attr('href') === current) {
					$this.addClass('active');
					$this.parents('.has-dropdown').addClass('menu-item-open')
				}
			});
		},


		modalOver: function() {
			$(document).ready(function() {
				// Declare a variable to keep track of the modal state
				var modalShown = false;

				// Function to show the modal after a delay
				function showModal() {
					if (!modalShown) {
						setTimeout(function() {
							$("#myModal-1").modal('show');
							modalShown = true; // Set the flag to true when the modal is shown
						}, 2000);
					}
				}

				// Show the modal when the document is ready
				showModal();

				// Set the flag to false when the modal is closed
				$('#myModal-1').on('hidden.bs.modal', function() {
					modalShown = false;
				});
			});

		},


		darklightSwitcher: function() {
			let html = document.documentElement;
			let rtsTheme = localStorage.theme;
			let rtsThemeLayout = localStorage.layout;
			let rtsThemeNavbar = localStorage.navbar;

			let darkMode = rtsTheme === "dark";
			let rtlLayout = rtsThemeLayout === "rtl";
			let topMenu = rtsThemeNavbar === "top";

			// Theme Mode Toggle 
			if (rtsTheme) {
				html.setAttribute("data-theme", rtsTheme);

				if (rtsTheme === "dark") {
					localStorage.theme === "dark"
					$(".rts-customizer__btn--light").removeClass("active");
					$(".rts-customizer__btn--dark").addClass("active");

				} else {
					localStorage.theme === "light"
				}
			}

			// Theme Layout Toggle
			if (rtsThemeLayout) {

				html.setAttribute("dir", rtsThemeLayout);

				if (rtsThemeLayout === "rtl") {
					localStorage.themeLayout === "rtl"
					$(".rts-customizer__btn--ltr").removeClass("active");
					$(".rts-customizer__btn--rtl").addClass("active");
				} else {
					localStorage.themeLayout === "ltr"
				}
			}

			// RTL Layout
			function rtlTheme(e) {
				let rtsThemeLayout = "rtl";
				localStorage.layout = rtsThemeLayout;
				document.documentElement.setAttribute("dir", rtsThemeLayout);

				rtlLayout = true;
			}

			// LTR Layout
			function ltrTheme(e) {
				let rtsThemeLayout = "ltr";
				localStorage.layout = rtsThemeLayout;
				document.documentElement.setAttribute("dir", rtsThemeLayout);

				rtlLayout = false;
			}

			// LTR Layout Add
			$(".rts-customizer__btn--ltr").click(function() {
				$(".rts-customizer__btn--rtl").removeClass("active");
				$(".rts-customizer__btn--ltr").addClass("active");

				ltrTheme();

				if ($("body").hasClass("layout-rtl")) {
					$("body").removeClass("layout-rtl");
				}
				$('html').attr('dir', 'ltr');
				$("body").addClass("layout-ltr");
			})

			// RTL Layout Add
			$(".rts-customizer__btn--rtl").click(function() {
				$(".rts-customizer__btn--ltr").removeClass("active");
				$(".rts-customizer__btn--rtl").addClass("active");

				rtlTheme();
			})
			$('.button-setting-rtl-ltr').click(function() {
				$('.rts-ltr-rtl-setting-button-area').toggleClass("active");
			})

		},


	}

	rtsJs.m();
})(jQuery, window)



function zoom(e) {
	var zoomer = e.currentTarget;
	e.offsetX ? offsetX = e.offsetX : offsetX = e.touches[0].pageX
	e.offsetY ? offsetY = e.offsetY : offsetX = e.touches[0].pageX
	x = offsetX / zoomer.offsetWidth * 100
	y = offsetY / zoomer.offsetHeight * 100
	zoomer.style.backgroundPosition = x + '% ' + y + '%';
}

$(document).ready(function() {
	// Hàm gọi API tỉnh thành
	function getProvinces() {
		$.ajax({
			url: "https://provinces.open-api.vn/api/p",  // API tỉnh thành Việt Nam
			type: "GET",
			success: function(data) {
				var provinceSelect = $("#province");
				provinceSelect.empty();
				provinceSelect.append('<option value="">Chọn Tỉnh/Thành phố</option>');

				data.forEach(function(province) {
					provinceSelect.append(`<option value="${province.code}">${province.name}</option>`);
				});
			},
			error: function() {
				alert("Lỗi khi tải dữ liệu tỉnh/thành phố.");
			}
		});
	}

	// Hàm gọi API quận/huyện theo tỉnh
	function getDistricts(provinceCode) {
		$.ajax({
			url: "https://provinces.open-api.vn/api/d", // API lấy toàn bộ quận/huyện
			type: "GET",
			success: function(data) {
				var districtSelect = $("#district");
				districtSelect.empty();
				districtSelect.append('<option value="">Chọn Quận/Huyện</option>');

				// Lọc quận/huyện theo mã tỉnh
				data.forEach(function(district) {
					if (district.province_code == provinceCode) {
						districtSelect.append(`<option value="${district.code}">${district.name}</option>`);
					}
				});
			},
			error: function() {
				alert("Lỗi khi tải dữ liệu quận/huyện.");
			}
		});
	}

	// Hàm gọi API xã/phường theo quận
	function getWards(districtCode) {
		$.ajax({
			url: `https://provinces.open-api.vn/api/w`,  // API xã/phường theo mã quận
			type: "GET",
			success: function(data) {
				var wardSelect = $("#ward");
				wardSelect.empty();
				wardSelect.append('<option value="">Chọn Phường/Xã</option>');

				// Thêm xã/phường vào dropdown
				data.forEach(function(ward) {
					if (ward.district_code == districtCode) {
						wardSelect.append(`<option value="${ward.code}">${ward.name}</option>`);
					}
				});
			},
			error: function() {
				alert("Lỗi khi tải dữ liệu phường/xã.");
			}
		});
	}

	// Gọi API tỉnh khi trang được tải
	getProvinces();

	// Khi chọn tỉnh, gọi API để tải quận/huyện
	$("#province").change(function() {
		var provinceCode = $(this).val();
		if (provinceCode) {
			getDistricts(provinceCode);
			$("#district").prop("disabled", false); // Kích hoạt dropdown quận/huyện
		} else {
			$("#district").prop("disabled", true).empty();
			$("#ward").prop("disabled", true).empty();
		}
	});

	// Khi chọn quận, gọi API để tải xã/phường
	$("#district").change(function() {
		var districtCode = $(this).val();
		if (districtCode) {
			getWards(districtCode);
			$("#ward").prop("disabled", false); // Kích hoạt dropdown phường/xã
		} else {
			$("#ward").prop("disabled", true).empty();
		}
	});

	// Xử lý khi form được submit
	$("#deliveryForm").submit(function(e) {
		e.preventDefault();
		var formData = $(this).serialize();
		console.log(formData); // Đây là nơi bạn có thể xử lý gửi dữ liệu đi

		alert("Địa chỉ đã được xác nhận!");
	});
});

function toggleHiddenInput(checkbox) {
	// Lấy đối tượng checkbox và hidden input tương ứng
	var hiddenInput = checkbox.previousElementSibling;

	// Cập nhật giá trị của hidden input tùy thuộc vào trạng thái checkbox
	if (checkbox.checked) {
		hiddenInput.value = "1";  // Khi checkbox được chọn
	} else {
		hiddenInput.value = "0";  // Khi checkbox không được chọn
	}
}
window.addEventListener('load', updateTotal);
let currentMethodElement, currentCostElement;

// Mở popup
function openPopup(event) {
	const popup = document.getElementById("popup-vanchuyen");
	popup.style.display = "block";
	document.documentElement.classList.add("no-scroll");

	// Tìm phần tử liên quan đến sản phẩm hiện tại
	const productInfo = event.target.closest('.product-info');
	currentMethodElement = productInfo.querySelector('.shippingMethod');
	currentCostElement = productInfo.querySelector('.shippingCost');

	// Lấy phương thức vận chuyển hiện tại
	const currentMethod = currentMethodElement.textContent.trim();

	// Đồng bộ radio button với phương thức vận chuyển hiện tại
	let found = false;
	document.querySelectorAll('input[name="shipping"]').forEach(radio => {
		if (radio.value === currentMethod) {
			radio.checked = true;
			found = true;
		}
	});

	// Nếu không tìm thấy, chọn mặc định radio đầu tiên
	if (!found) {
		document.querySelector('input[name="shipping"]').checked = true;
	}
}

// Đóng popup
function closePopup() {
	const popup = document.getElementById("popup-vanchuyen");
	popup.style.display = "none";
	document.documentElement.classList.remove("no-scroll");
}
function closeAddressModel() {
	const modal = bootstrap.Modal.getInstance(document.getElementById('deliveryModal'));
	modal.hide();

}
function removeAddress() {
	// Tìm thẻ cha gần nhất có class 'address'
	const parentDiv = event.target.closest(".address");

	// Lấy ID của địa chỉ từ thẻ input hidden
	const addressId = document.getElementById("addressid").value;

	console.log(addressId)
	console.log(parentDiv)
	// Gửi yêu cầu xóa đến server, chỉ gửi ID (không cần bọc trong đối tượng JSON)
	fetch("/user/api/address/delete", {
		method: "POST",
		headers: {
			"Content-Type": "application/json", // Đảm bảo là JSON
		},
		body: addressId, // Gửi trực tiếp ID
	})
		.then(response => {
			if (response.ok) {
				parentDiv.remove(); // Xóa thẻ cha khỏi giao diện
			} else {
				console.error("Có lỗi khi xóa địa chỉ.");
			}
		})
		.catch(error => {
			console.error("Có lỗi xảy ra khi xóa địa chỉ: " + error.message);
		});
}

// Xác nhận và cập nhật thông tin
function confirmSelection() {
	const selectedOption = document.querySelector('input[name="shipping"]:checked').value;

	// Cập nhật giá tiền dựa vào lựa chọn
	const priceMap = {
		"Nhanh": "20.000",
		"Hỏa Tốc": "45.000"
	};

	// Cập nhật phần tử phương thức vận chuyển và giá
	currentMethodElement.textContent = selectedOption;
	currentCostElement.textContent = priceMap[selectedOption] + "₫";
	updateTotalForOrder(currentMethodElement.closest('.product-info'));
	updateTotal();
	closePopup();
}
function getProvincesName(provinceCode) {
	return new Promise((resolve, reject) => {
		$.ajax({
			url: "https://provinces.open-api.vn/api/p",  // API lấy toàn bộ tỉnh/thành phố
			type: "GET",
			success: function(data) {
				const province = data.find(province => province.code == provinceCode);
				if (province) {
					resolve(province.name); // Trả về tên tỉnh/thành phố
				} else {
					reject(`Không tìm thấy tỉnh/thành phố với mã: ${provinceCode}`);
				}
			},
			error: function() {
				reject("Lỗi khi tải dữ liệu tỉnh/thành phố.");
			}
		});
	});
}



function getDistrictName(districtCode) {
	return new Promise((resolve, reject) => {
		$.ajax({
			url: "https://provinces.open-api.vn/api/d", // API lấy danh sách các quận/huyện
			type: "GET",
			success: function(data) {
				// Tìm quận/huyện dựa trên districtCode
				const district = data.find(district => district.code == districtCode);
				if (district) {
					resolve(district.name); // Trả về tên quận/huyện
				} else {
					reject(`Không tìm thấy quận/huyện với mã: ${districtCode}`);
				}
			},
			error: function() {
				reject("Lỗi khi tải dữ liệu quận/huyện.");
			}
		});
	});
}


function getWardName(wardCode) {
	return new Promise((resolve, reject) => {
		$.ajax({
			url: "https://provinces.open-api.vn/api/w",  // API lấy danh sách xã/phường
			type: "GET",
			success: function(data) {
				// Tìm xã/phường dựa trên wardCode
				const ward = data.find(ward => ward.code == wardCode);
				if (ward) {
					resolve(ward.name); // Trả về tên xã/phường
				} else {
					reject(`Không tìm thấy xã/phường với mã: ${wardCode}`);
				}
			},
			error: function() {
				reject("Lỗi khi tải dữ liệu xã/phường.");
			}
		});
	});
}
async function saveAddress() {
	// Lấy giá trị từ các trường input
	const uname = document.getElementById("uname").value;
	const phone = document.getElementById("phone").value;
	const provinceCode = document.getElementById("province").value;
	const districtCode = document.getElementById("district").value;
	const wardCode = document.getElementById("ward").value;
	const detail = document.getElementById("detail").value;

	try {
		// Lấy tên tỉnh, huyện, xã từ các API bất đồng bộ
		const province = await getProvincesName(provinceCode);
		const district = await getDistrictName(districtCode);
		const ward = await getWardName(wardCode);

		// Tạo object addressData
		const addressData = {
			uname: uname,
			phone: phone,
			province: province,
			district: district,
			ward: ward,
			detail: detail
		};
		// Gửi dữ liệu qua AJAX
		$.ajax({
			url: '/user/api/address/save', // Địa chỉ API
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(addressData), // Dữ liệu gửi đi dưới dạng JSON
			success: function(response) {
				// Tạo một HTML mới cho địa chỉ vừa thêm
				const newAddressHTML = `
			                    <div class="col-6 address">
			                        <div class="align-items-start" style="padding: 10px; height: 100%">
			                            <div style="padding: 10px; height: 80%">
			                                <p><strong>${addressData.uname}</strong></p>
			                                <p>${addressData.phone}</p>
			                                <p>${addressData.detail} ${addressData.ward} ${addressData.district} ${addressData.province}</p>
			                            </div>
			                            <div class="d-flex" style="height: 20%; width: 100%; justify-content: flex-end; display: flex;">
			                                <i class="fa-solid fa-pencil-alt" style="padding-right: 10px;"></i>
			                                <i class="fa-solid fa-trash-alt" style="padding-right: 10px;"></i>
			                                <a>Mac dinh</a>
			                            </div>
			                        </div>
			                    </div>
			                `;

				// Chèn vào container chứa các địa chỉ
				$('.row-address').append(newAddressHTML);

				// Hiển thị thông báo
				alert("Địa chỉ đã được lưu thành công!");
			},
			error: function(error) {
				alert("Có lỗi xảy ra: " + error.responseText);
			}
		});
	} catch (error) {
		// Xử lý lỗi nếu API không lấy được tên tỉnh, huyện, xã
		console.error("Lỗi khi lấy dữ liệu:", error);
		alert("Không thể lưu địa chỉ. Vui lòng kiểm tra lại thông tin.");
	}
}
// Gán sự kiện
document.querySelectorAll('.changeButton').forEach(button => {
	button.addEventListener("click", openPopup);
});

document.querySelectorAll('.confirm-button').forEach(button => {
	button.addEventListener("click", confirmSelection);
	button.addEventListener("click", closePopup);
});

document.querySelectorAll('.saveAddressBtn').forEach(button => {
	button.addEventListener("click", saveAddress);
	button.addEventListener("click", closeAddressModel);
});

document.querySelectorAll('.close-button').forEach(button => {
	button.addEventListener("click", closePopup);
});
document.querySelectorAll('.remove-address').forEach(button => {
	button.addEventListener("click", removeAddress);
});
function updateTotal() {
	let totalItems = 0;
	let totalShipping = 0;

	// Lấy tổng tiền hàng và phí vận chuyển từ tất cả các sản phẩm
	document.querySelectorAll('.product-info').forEach(product => {
		const itemCost = parseInt(product.querySelector('.title-sp-1').textContent.replace(/\D/g, '')); // Tiền hàng
		const shippingCost = parseInt(product.querySelector('.shippingCost').textContent.replace(/\D/g, '')); // Phí vận chuyển

		totalItems += itemCost;
		totalShipping += shippingCost;
	});

	// Cập nhật giá trị tổng tiền hàng, phí vận chuyển và tổng thanh toán
	document.querySelector('.tien-tong-items').textContent = totalItems.toLocaleString() + "₫";
	document.querySelector('.tien-tong-shipping').textContent = totalShipping.toLocaleString() + "₫";
	document.querySelector('.tien-tong-total').textContent = (totalItems + totalShipping).toLocaleString() + "₫";
}
function updateTotalForOrder(orderElement) {
	let totalItems = 0;
	let shippingCost = 0;

	// Tìm các phần tử liên quan trong đơn hàng cụ thể
	totalItems = parseInt(orderElement.querySelector('.title-sp-1').textContent.replace(/\D/g, '')) || 0;
	shippingCost = parseInt(orderElement.querySelector('.shippingCost').textContent.replace(/\D/g, '')) || 0;

					if (!this.classList.contains('active-btn')) {
						document.querySelectorAll('.btn-tt').forEach(btn => btn.classList.remove('active-btn'));
						this.classList.add('active-btn'); 
					}
				});
			});
			var swiper = new Swiper('.swiper-container', {
			  slidesPerView: 5,  // Show 5 items at once
			  spaceBetween: 10,   // Space between items
			  loop: true,         // Loop the slides
			  speed: 1000,        // Transition speed
			  autoplay: {
			      delay: 3000,             // Đặt độ trễ tự động chuyển slide là 5 giây (5000 ms)
			      disableOnInteraction: false,  // Không dừng autoplay khi người dùng tương tác
			    },
			  pagination: {
			    el: '.swiper-pagination',
			    clickable: true,  // Enable pagination clicks
			  }});
			  // Tính tổng thanh toán
	const total = totalItems + shippingCost;

	// Cập nhật tổng tiền cho đơn hàng này
	orderElement.querySelector('.tongtien span').textContent = "Tổng tiền: " + total.toLocaleString() + "₫";
}
window.addEventListener('load', () => {
	document.querySelectorAll('.product-info').forEach(order => {
		updateTotalForOrder(order);
	});
});
document.querySelectorAll('.btn-tt').forEach(button => {
	button.addEventListener('click', function(event) {
		event.stopPropagation();

		if (!this.classList.contains('active-btn')) {
			document.querySelectorAll('.btn-tt').forEach(btn => btn.classList.remove('active-btn'));
			this.classList.add('active-btn');
		}
	});
});

document.querySelectorAll('.quantity-edit').forEach(edit => {
	const valueInput = edit.querySelector('input');
	const minusButton = edit.querySelector('.button.minus');
	const plusButton = edit.querySelector('.button.plus');

	minusButton.addEventListener('click', () => {
		let quantity = parseInt(valueInput.value);
		if (quantity > 1) {
			quantity--;
			updateInputAndPrice(quantity, edit);
		}
	});

	// Sự kiện khi nhấn nút tăng
	plusButton.addEventListener('click', () => {
		let quantity = parseInt(valueInput.value);
		quantity++;
		updateInputAndPrice(quantity, edit);
	});

	// Hàm cập nhật giá trị input và tổng tiền
	function updateInputAndPrice(quantity, edit) {
		const priceElement = edit.closest('.item-parent').querySelector('.price p');
		const subtotalElement = edit.closest('.item-parent').querySelector('.subtotal p');
		const basePrice = parseFloat(priceElement.textContent.replace(/[^\d.]/g, '')) || 0;

		const subtotal = quantity * basePrice;
		subtotalElement.textContent = subtotal.toLocaleString('vi-VN') + " ₫";
	}
});
function toggleHiddenInput(checkbox) {
	// Lấy đối tượng checkbox và hidden input tương ứng
	var hiddenInput = checkbox.previousElementSibling;

	// Cập nhật giá trị của hidden input tùy thuộc vào trạng thái checkbox
	if (checkbox.checked) {
		hiddenInput.value = "1";  // Khi checkbox được chọn
	} else {
		hiddenInput.value = "0";  // Khi checkbox không được chọn
	}
}
document.querySelectorAll('.btn-dathang').forEach(button => {
	button.addEventListener("click", dathang);
});
function dathang()
{
	const addressId = document.querySelector('#addressid').value;
	   console.log(addressId)
	   // Lấy danh sách các productId và quantity từ các sản phẩm trong giỏ hàng
	   const productIds = [];
	   const quantities = [];

	   // Giả sử rằng mỗi sản phẩm có cấu trúc như sau:
	   // <input type="hidden" class="product-id" value="productId">
	   // <input type="number" class="product-quantity" value="quantity">
	   
	   document.querySelectorAll('.product-info').forEach(product => {
	       const productId = product.querySelector('.product-id').value;
	       const quantity = product.querySelector('.product-quantity').textContent;
	       
	       productIds.push(productId);
	       quantities.push(quantity);
	   });
		
	   // Tạo đối tượng dữ liệu để gửi
	   const orderData = {
	       addressId: addressId,
	       productIds: productIds,
	       quantities: quantities
	   };
		
	   // Gửi yêu cầu POST tới controller
	   $.ajax({
	       url: '/user/cart/payment',  // Địa chỉ API
	       type: 'POST',
	       contentType: 'application/json',  // Định dạng gửi đi là JSON
	       data: JSON.stringify(orderData),  // Dữ liệu gửi đi dưới dạng JSON
	       success: function(response) {
	           // Xử lý phản hồi từ server
	           alert("Tao don hang thanh cong");
			   window.location.href = '/user/cart';
	       },
	       error: function(error) {
	           // In lỗi nếu có
	           console.error('Lỗi khi đặt hàng:', error);
	       }
	   });
