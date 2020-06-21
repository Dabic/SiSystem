import {CompareCompetenceMainModel} from './compare-competence-main.model';

export class CompareCompetencesModel {
    constructor(public course: string, public myCompetences: CompareCompetenceMainModel[]) {
    }
}
