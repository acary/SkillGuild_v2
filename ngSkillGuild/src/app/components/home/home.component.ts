import { Component, OnInit } from '@angular/core';
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [NgbCarouselConfig]

})
export class HomeComponent implements OnInit {

  showNavigationArrows = false;
  showNavigationIndicators = true;

  images: any[] =
  [
    "assets/images/carousel_7.avif",
    "assets/images/carousel_8.avif",
    "assets/images/carousel_9.avif",
    "assets/images/carousel_10.avif",
    "assets/images/carousel_11.avif"
];

  constructor(config: NgbCarouselConfig) {
    config.showNavigationArrows = true;
    config.showNavigationIndicators = true;
   }

  ngOnInit(): void {
  }

}
