import {Directive, ElementRef, HostListener, Renderer2} from '@angular/core';
import {TreeService} from './tree.service';

@Directive({
  selector: '[appTree]'
})
export class TreeDirective {

  private collapsed = true;
  private clicks = 0;

  constructor(private elementRef: ElementRef, private renderer: Renderer2, private treeService: TreeService) {
    this.renderer.addClass(this.elementRef.nativeElement.parentNode, 'collapsed');
  }

  @HostListener('click')
  onSingleClick() {
    this.clicks++;
    if (this.clicks === 1) {
      setTimeout(() => {
        if (this.clicks === 2) {
          this.treeService.entityDoubleClicked(this.elementRef.nativeElement.innerText);
        } else {
          this.collapsed = !this.collapsed;
          if (this.collapsed) {
            this.renderer.addClass(this.elementRef.nativeElement.parentNode, 'collapsed');
          } else {
            this.renderer.removeClass(this.elementRef.nativeElement.parentNode, 'collapsed');
          }
          this.renderer.addClass(this.elementRef.nativeElement, 'highlight');
          setTimeout(() => {
            this.renderer.removeClass(this.elementRef.nativeElement, 'highlight');
          }, 500);
        }
        this.clicks = 0;
      }, 200);
    }
  }


}
