export class ReportModel {
    constructor(public patternName: string, public equality: string, public range: string, public includedFields: string[]) {
    }
}
