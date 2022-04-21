import { Card, CardContent, CardHeader, CardActionArea } from '@material-ui/core';

export const CustomCardComponent = (props) => {
    const { children, title, className, color } = props
    return <Card className={className} style={{ border: '2px solid #2D63AB' }}>
        <CardHeader style={{ backgroundColor: color }} title={title} />
        <CardActionArea>{children}</CardActionArea>
    </Card>
}