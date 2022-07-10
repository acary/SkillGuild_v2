import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Interaction } from 'src/app/models/interaction';
import { InteractionService } from 'src/app/services/interaction.service';

@Component({
  selector: 'app-interaction',
  templateUrl: './interaction.component.html',
  styleUrls: ['./interaction.component.css']
})
export class InteractionComponent implements OnInit {


  interactions: Interaction[] = [];

  newInteraction: Interaction = new Interaction();

  selected: Interaction | null = null;

  showAllInteractions: boolean = true;

  createForm: boolean = false;

  createInteractionForm: any;

  isEditing = false;

  editInteraction: Interaction | null = null;

  constructor(
    private interactionSvc: InteractionService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllInteractions();
  }

  getAllInteractions() {
    this.interactionSvc.index().subscribe(interactions => {
      this.interactions = interactions;
    });
  }

  createInteraction(interaction: Interaction): void {
    this.interactionSvc.create(interaction).subscribe({
      next: interaction => {
        console.log("Created successfully: " + interaction.id);
        this.interactions.push(interaction);
        this.newInteraction = new Interaction();
        this.toggleAllInteractions();
        this.getAllInteractions();
      },
      error: (err) => {
        console.error('Error creating interaction: ', err);
      }
    })
  }

  setEditInteraction = () => {
    this.editInteraction = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editInteraction = null;
  }

  updateInteraction = (interaction: Interaction) => {
    this.interactionSvc.update(interaction).subscribe(
      {
        next: () => {
          console.log("Updated interaction successfully: " + interaction.id);
          this.selected = null;
          this.editInteraction = null;
          this.displayAll();
          this.getAllInteractions();
        },
        error: (err: any) => console.error('Error updating interaction: ', err)
      }
    );
  }

  displayAll(): void {
    this.selected = null;
  }

  getInteractionById(id: number) {
    this.interactionSvc.show(id).subscribe(
      interaction => {
        return interaction;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteInteraction(id: number) {
    this.interactionSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllInteractions()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllInteractions = () => {
    this.showAllInteractions = !this.showAllInteractions;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllInteractions = !this.showAllInteractions;
  }

  createFormInit(fb: FormBuilder) {
    this.createInteractionForm = this.fb.group({
      id: [0],
      createdOn: [''],
      contentId: [0],
      userId: [0],
      interactionTypeId: [0]
    });
    this.createInteractionForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewInteraction() {
    let interaction: Interaction = {
      id: this.createInteractionForm.get('id').value,
      createdOn: this.createInteractionForm.get('createdOn').value,
      contentId: 0,
      userId: 0,
      interactionTypeId: 0
    }
    this.isEditing = false;
    console.log(interaction);
    this.createInteraction(interaction);
    console.log(interaction)
  }

}
