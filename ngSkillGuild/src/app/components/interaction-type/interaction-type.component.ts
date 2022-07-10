import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InteractionType } from 'src/app/models/interaction-type';
import { InteractionTypeService } from 'src/app/services/interaction-type.service';

@Component({
  selector: 'app-interaction-type',
  templateUrl: './interaction-type.component.html',
  styleUrls: ['./interaction-type.component.css']
})
export class InteractionTypeComponent implements OnInit {

  interactionTypes: InteractionType[] = [];

  newInteractionType: InteractionType = new InteractionType();

  selected: InteractionType | null = null;

  showAllInteractionTypes: boolean = true;

  createForm: boolean = false;

  createInteractionTypeForm: any;

  isEditing = false;

  editInteractionType: InteractionType | null = null;

  constructor(
    private interactionTypeSvc: InteractionTypeService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllInteractionTypes();
  }
  getAllInteractionTypes() {
    this.interactionTypeSvc.index().subscribe(interactionTypes => {
      this.interactionTypes = interactionTypes;
    });
  }

  createInteractionType(interactionType: InteractionType): void {
    this.interactionTypeSvc.create(interactionType).subscribe({
      next: interactionType => {
        console.log("Created successfully: " + interactionType.id);
        this.interactionTypes.push(interactionType);
        this.newInteractionType = new InteractionType();
        this.toggleAllInteractionTypes();
        this.getAllInteractionTypes();
      },
      error: (err) => {
        console.error('Error creating interactionType: ', err);
      }
    })
  }

  setEditInteractionType = () => {
    this.editInteractionType = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editInteractionType = null;
  }

  updateInteractionType = (interactionType: InteractionType) => {
    this.interactionTypeSvc.update(interactionType).subscribe(
      {
        next: () => {
          console.log("Updated interactionType successfully: " + interactionType.id);
          this.selected = null;
          this.editInteractionType = null;
          this.displayAll();
          this.getAllInteractionTypes();
        },
        error: (err: any) => console.error('Error updating interactionType: ', err)
      }
    );
  }

  displayAll(): void {
    this.selected = null;
  }

  getInteractionTypeById(id: number) {
    this.interactionTypeSvc.show(id).subscribe(
      interactionType => {
        return interactionType;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteInteractionType(id: number) {
    this.interactionTypeSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllInteractionTypes()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllInteractionTypes = () => {
    this.showAllInteractionTypes = !this.showAllInteractionTypes;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllInteractionTypes = !this.showAllInteractionTypes;
  }

  createFormInit(fb: FormBuilder) {
    this.createInteractionTypeForm = this.fb.group({
      id: [0],
      name: [''],
      imgUrl: ['https://via.placeholder.com/150']
    });
    this.createInteractionTypeForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewInteractionType() {
    let interactionType: InteractionType = {
      id: this.createInteractionTypeForm.get('id').value,
      name: this.createInteractionTypeForm.get('name').value,
      imgUrl: this.createInteractionTypeForm.get('imgUrl').value,
    }
    this.isEditing = false;
    console.log(interactionType);
    this.createInteractionType(interactionType);
  }
}
